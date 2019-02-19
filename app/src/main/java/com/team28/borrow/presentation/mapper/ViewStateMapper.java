package com.team28.borrow.presentation.mapper;

import android.content.Context;

//V refers to viewmodel and D is Domain model
public abstract class ViewStateMapper<V, D> {

    private final Context context;

    public ViewStateMapper(Context context) {
        this.context = context;
    }

    public abstract V map(D domainModel);

    public Context getContext() {
        return context;
    }
}
