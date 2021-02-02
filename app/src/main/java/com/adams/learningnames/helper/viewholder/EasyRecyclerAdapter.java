package com.adams.learningnames.helper.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 * @author Manuel Adams
 * @since 2019-03-03
 */
public class EasyRecyclerAdapter<T> extends RecyclerView.Adapter<EasyRecyclerAdapter.EasyRecyclerViewHolder<T>> {

    private final int itemLayoutRes;
    private final List<T> list;
    private final ViewHolderGenerator<? extends EasyRecyclerAdapter.EasyRecyclerViewHolder<T>> generator;

    public EasyRecyclerAdapter(@LayoutRes int itemLayoutRes, List<T> list, ViewHolderGenerator<? extends EasyRecyclerAdapter.EasyRecyclerViewHolder<T>> generator) {
        this.itemLayoutRes = itemLayoutRes;
        this.list = list;
        this.generator = generator;
    }

    @NonNull
    @Override
    public EasyRecyclerViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext())
                .inflate(itemLayoutRes, parent, false);
        return generator.createViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(@NonNull EasyRecyclerViewHolder<T> holder, int position) {
        holder.internalSaveAndApply(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static abstract class EasyRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

        private T data;

        protected EasyRecyclerViewHolder(@NonNull View holderView) {
            super(holderView);
            ButterKnife.bind(this, holderView);
        }

        void internalSaveAndApply(T data) {
            this.data = data;
            apply(data);
        }

        protected T getData() {
            return data;
        }

        protected abstract void apply(T t);
    }

    public interface ViewHolderGenerator<V extends EasyRecyclerAdapter.EasyRecyclerViewHolder<?>> {

        V createViewHolder(View holderView);
    }
}
