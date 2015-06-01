/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android;

import com.example.android.common.logger.Log;
import com.example.android.common.view.SlidingTabLayout;
import com.example.android.slidingtabsbasic.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A basic sample which shows how to use {@link com.example.android.common.view.SlidingTabLayout}
 * to display a custom {@link android.support.v4.view.ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class Demo extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);

        //TODO 设置适配器，设置Viewpager保存的页面，为mSlidingTabLayout设置viewpager
        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setOffscreenPageLimit(4);
        mSlidingTabLayout.setViewPager(mViewPager);
    }


    /**
     * 继承pagerAdapter的适配器需要实现以下方法
     */
    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return 返回需要的数量
         */
        @Override
        public int getCount() {
            return 40;
        }

        /**
         * 判断是否instantiateItem返回的对象是不是一个view，如果不是一个view，那么viewpager就不会显示内容
         * 所以可以设置为return true
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }


        /**
         * 返回此viewpager的标题，很多其他和view交互的类很可能需要显示标题，如果不需要标题显示，也可以不设置
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * 初始化需要显示的view，这个地方需要吧 view放入container中可以这样初始一个view
         *  View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
            container, false);
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                    container, false);
            //TODO 需要吧显示的view放入container中
            container.addView(view);

            //TODO 可以在此地初始化需要显示view的内容，当然可以让view自己管理需要显示的内容
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");
            // Return the View
            return view;
        }

        /**
         * 从container删除不需要的view
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //TODO 需要移除view。。可能会增加性能，具体不清楚
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

    }
}
