package com.qhc.windpower.base.adapter.lrecycleview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.qhc.windpower.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 封装adapter
 */
public class ListBaseAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {
    protected Context mContext;
    private LayoutInflater mInflater;
    private int layoutId;
    private int variableId;

    protected List<T> mDataList = new ArrayList<>();

    public void init(Context context, int layoutId, Collection<T> list) {
        try {
            String className = context.getApplicationContext().getPackageName() + ".BR";
            Class brClass = Class.forName(className);
            Field viewModelField = brClass.getField("viewModel");
            int resId = viewModelField.getInt(brClass);
            mContext = context;
            this.layoutId = layoutId;
            this.variableId = resId;
            mInflater = LayoutInflater.from(context);
            setDataList(list);
        } catch (Exception e) {
            LogUtil.e("Adapter BR ERROR:", e);
        }
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(mInflater, layoutId, parent, false);
        return new SuperViewHolder(dataBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        onBindItemHolder(holder, position);
    }

    // 局部刷新关键：带payload的这个onBindViewHolder方法必须实现
    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindItemHolder(holder, position, payloads);
        }
    }

    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ViewDataBinding dataBinding = DataBindingUtil.getBinding(holder.item);
        dataBinding.setVariable(variableId, mDataList.get(position));
    }

    public void onBindItemHolder(SuperViewHolder holder, int position, List<Object> payloads) {
        ViewDataBinding dataBinding = DataBindingUtil.getBinding(holder.item);
        dataBinding.setVariable(variableId, payloads.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if (position != (getDataList().size())) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, this.mDataList.size() - position);
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }
}
