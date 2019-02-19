package com.team28.borrow.presentation.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseRecyclerViewAdapter<T, V extends BaseViewHolder<T>>
        extends RecyclerView.Adapter<V> {
    private List<T> models;
    private PublishSubject<T> modelSubject;
    private PublishSubject<Integer> adapterPosition;



    public BaseRecyclerViewAdapter() {
        models = new ArrayList<>();
        modelSubject = PublishSubject.create();
        adapterPosition = PublishSubject.create();
    }

    @LayoutRes
    public abstract int getItemLayoutResource();

    public abstract V getViewHolder(View view);

    @Override public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(getItemLayoutResource(), parent, false);
        V viewHolder = getViewHolder(view);

        view.setOnClickListener(v -> {
            if (viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                modelSubject.onNext(models.get(viewHolder.getAdapterPosition()));
                adapterPosition.onNext(viewHolder.getAdapterPosition()
                );
            }
        });

        return viewHolder;
    }

    @Override public void onBindViewHolder(@NonNull V holder, int position) {
        holder.bind(models.get(position));
    }

    @Override public int getItemCount() {
        return models.size();
    }

    public Observable<T> itemClickStream() {
        return modelSubject;
    }

    public Observable<Integer> itemPositonClickStream() {
        return adapterPosition;
    }

    public void setModels(List<T> models) {
        this.models = models;
        notifyDataSetChanged();
    }

}