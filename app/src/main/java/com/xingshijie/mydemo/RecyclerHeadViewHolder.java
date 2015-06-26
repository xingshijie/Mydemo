package com.xingshijie.mydemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/25 0025.
 * 注意，如果头部第一个view的高度为零，则会产生出乎意料的变化，如果外部包含一个Swipelayout，则不会发生下拉刷新
 * 事件，原因不明
 */
public class RecyclerHeadViewHolder extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List items=new ArrayList();
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    /**
     * 判断此位置的类型
     * @param position
     * @return
     */
    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent,
                    false));
        } else if (viewType == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent,
                    false));
        }


        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            onBindViewItemHolder((ItemViewHolder) holder, position - 1);
            //cast holder to VHItem and set data
        } else if (holder instanceof HeadViewHolder) {
            //cast holder to VHHeader and set data for header.
            onBindViewHeadHolder((HeadViewHolder)holder);
        }

    }
    public void onBindViewItemHolder(ItemViewHolder holder, int position) {

    }

    public void onBindViewHeadHolder(HeadViewHolder holder){

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof  ItemViewHolder) ((ItemViewHolder) holder).mainArea.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() +1: 1;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public View mainArea;
        public ItemViewHolder(View view) {
            super(view);
        }
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {
        public View mainArea;
        public HeadViewHolder(View view) {
            super(view);
        }
    }
}
