package com.whitelaning.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends BaseActivity {
    // 导入的三个layout布局文件
    private View view1;
    private View view2;
    private View view3;

    private ViewPager viewPager;//ViewPager的实例

    private List<View> viewList;//View的集合
    private List<String> titleList;  //标题的集合

    private LayoutInflater inflater; //布局填充器

    private PagerAdapter pagerAdapter;//ViewPager适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        initData();
    }

    private void initData() {

        pagerAdapter = new MyPagerAdapter();

        viewList = new ArrayList();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        titleList = new ArrayList();//将要分页显示的Title数据装入数组中
        titleList.add("The one page");
        titleList.add("The two page");
        titleList.add("The three page");

        viewPager.setAdapter(pagerAdapter);
    }

    private void initView() {

        inflater = getLayoutInflater();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        view1 = inflater.inflate(R.layout.view_pager_item_1, null);
        view2 = inflater.inflate(R.layout.view_pager_item_2, null);
        view3 = inflater.inflate(R.layout.view_pager_item_3, null);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size(); // 返回要滑动的View的个数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//判断从instantiateItem中返回的view是不是同一个view
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position)); // 从当前container中删除指定位置（position）的View
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position)); // 将当前视图添加到container中
            return viewList.get(position); // 返回当前View
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);//根据位置返回当前所对应的标题
        }
    }
}
