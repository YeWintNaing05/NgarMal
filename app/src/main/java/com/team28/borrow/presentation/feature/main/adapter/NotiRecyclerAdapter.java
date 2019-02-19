package com.team28.borrow.presentation.feature.main.adapter;


import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseRecyclerViewAdapter;
import com.team28.borrow.presentation.model.NotiModel;

public class NotiRecyclerAdapter extends BaseRecyclerViewAdapter<NotiModel, NotiItemViewHolder> {


    @Override
    public int getItemLayoutResource() {
        return R.layout.item_noti;
    }

    @Override
    public NotiItemViewHolder getViewHolder(View view) {
        return new NotiItemViewHolder(view);
    }
}
