package com.whitelaning.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Random;

public class FillColorByLayerView extends ImageView {

    private LayerDrawable mDrawables;
    private Random random = new Random();

    public FillColorByLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawables = (LayerDrawable) getBackground();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mDrawables.getIntrinsicWidth(), mDrawables.getIntrinsicHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Drawable drawable = findDrawable(x, y);
            if (drawable != null) {
                drawable.setColorFilter(randomColor(), PorterDuff.Mode.SRC_IN);
            }
        }

        return super.onTouchEvent(event);
    }

    private int randomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private Drawable findDrawable(float x, float y) {
        final int numberOfLayers = mDrawables.getNumberOfLayers();

        Drawable drawable;
        Bitmap bitmap;

        for (int i = numberOfLayers - 1; i >= 0; i--) {
            drawable = mDrawables.getDrawable(i);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            try {
                int pixel = bitmap.getPixel((int) x, (int) y);
                if (pixel == Color.TRANSPARENT) {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
            return drawable;
        }
        return null;
    }
}
