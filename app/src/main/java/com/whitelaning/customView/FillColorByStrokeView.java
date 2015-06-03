package com.whitelaning.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.whitelaning.whiteframe.R;

import java.util.Random;
import java.util.Stack;

/**
 * Use Hongyang's blog http://blog.csdn.net/lmj623565791/article/details/45954255
 */
public class FillColorByStrokeView extends ImageView {

    private Bitmap bitmap;
    private int borderColor = -1;//边界的颜色
    private boolean hasBorderColor = false;
    private Stack<Point> mStacks = new Stack();

    public FillColorByStrokeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FillColorImagerView);
        borderColor = typedArray.getColor(R.styleable.FillColorImagerView_border_color, -1);
        hasBorderColor = (borderColor != -1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        //以宽度为标准，等比例缩放view的高度
        setMeasuredDimension(
                viewWidth,
                getDrawable().getIntrinsicHeight() * viewWidth / getDrawable().getIntrinsicWidth());

        //根据drawable，获取bitmap
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        this.bitmap = Bitmap.createScaledBitmap(
                bitmapDrawable.getBitmap(),
                getMeasuredWidth(), getMeasuredHeight(),
                false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            fillColorToSameArea(x, y);//填色
        }

        return super.onTouchEvent(event);
    }

    /**
     * 根据x,y获得改点颜色，进行填充
     *
     * @param x
     * @param y
     */
    private void fillColorToSameArea(int x, int y) {
        Bitmap bitmap = this.bitmap;

        int pixel = bitmap.getPixel(x, y);

        if (pixel == Color.TRANSPARENT || (hasBorderColor && borderColor == pixel)) {
            return;
        }

        int newColor = randomColor();//获取填充的颜色

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //拿到该bitmap的颜色数组
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        fillColor(pixels, width, height, pixel, newColor, x, y);//填色

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);//重新设置bitmap

        setImageDrawable(new BitmapDrawable(null, bitmap));
    }


    /**
     * @param pixels   像素数组
     * @param w        宽度
     * @param h        高度
     * @param pixel    当前点的颜色
     * @param newColor 填充色
     * @param x        横坐标
     * @param y        纵坐标
     */
    private void fillColor(int[] pixels, int w, int h, int pixel, int newColor, int x, int y) {
        /**
         * 将种子点(x, y)入栈；
         */
        mStacks.push(new Point(x, y));
        /**
         * 判断栈是否为空， 如果栈为空则结束算法，否则取出栈顶元素作为当前扫描线的种子点(x, y)，y是当前的扫描线；
         */
        while (!mStacks.isEmpty()) {
            /**
             * 从种子点(x, y)出发，沿当前扫描线向左、右两个方向填充，直到边界。分别标记区段的左、右端点坐标为xLeft和xRight；
             */
            Point seed = mStacks.pop();

            int leftCount = fillLineLeft(pixels, pixel, w, h, newColor, seed.x, seed.y);
            int left = seed.x - leftCount + 1;

            int rightCount = fillLineRight(pixels, pixel, w, h, newColor, seed.x + 1, seed.y);
            int right = seed.x + rightCount;


            /**
             * 分别检查与当前扫描线相邻的y - 1和y + 1两条扫描线在区间[xLeft, xRight]中的像素，从xRight开始向xLeft方向搜索，假设扫描的区间为AAABAAC（A为种子点颜色），
             * 那么将B和C前面的A作为种子点压入栈中
             */

            if (seed.y - 1 >= 0) {
                findSeedInNewLine(pixels, pixel, w, h, seed.y - 1, left, right);//从y-1找种子
            }

            if (seed.y + 1 < h) {
                findSeedInNewLine(pixels, pixel, w, h, seed.y + 1, left, right);//从y+1找种子
            }

        }
    }

    /**
     * 在新行找种子节点
     *
     * @param pixels
     * @param pixel
     * @param w
     * @param h
     * @param i
     * @param left
     * @param right
     */
    private void findSeedInNewLine(int[] pixels, int pixel, int w, int h, int i, int left, int right) {
        /**
         * 获得该行的开始索引
         */
        int begin = i * w + left;
        /**
         * 获得该行的结束索引
         */
        int end = i * w + right;

        boolean hasSeed = false;

        int rx;
        int ry;

        ry = i;

        /**
         * 从end到begin，找到种子节点入栈（AAABAAAB，则B前的A为种子节点）
         */
        while (end >= begin) {
            if (pixels[end] == pixel) {
                if (!hasSeed) {
                    rx = end % w;
                    mStacks.push(new Point(rx, ry));
                    hasSeed = true;
                }
            } else {
                hasSeed = false;
            }
            end--;
        }
    }

    /**
     * 往右填色
     *
     * @return 返回填充的个数
     */
    private int fillLineRight(int[] pixels, int pixel, int w, int h, int newColor, int x, int y) {
        int count = 0;

        while (x < w) {
            int index = y * w + x;//拿到索引
            if (needFillPixel(pixels, pixel, index)) {
                pixels[index] = newColor;
                count++;
                x++;
            } else {
                break;
            }

        }

        return count;
    }


    /**
     * 往左填色
     *
     * @return 返回填色的数量值
     */
    private int fillLineLeft(int[] pixels, int pixel, int w, int h, int newColor, int x, int y) {
        int count = 0;
        while (x >= 0) {

            int index = y * w + x;//计算出索引

            if (needFillPixel(pixels, pixel, index)) {
                pixels[index] = newColor;
                count++;
                x--;
            } else {
                break;
            }

        }
        return count;
    }

    private boolean needFillPixel(int[] pixels, int pixel, int index) {
        if (hasBorderColor) {
            return pixels[index] != borderColor;
        } else {
            return pixels[index] == pixel;
        }
    }

    /**
     * 返回一个随机颜色
     *
     * @return 颜色代码
     */
    private int randomColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
}
