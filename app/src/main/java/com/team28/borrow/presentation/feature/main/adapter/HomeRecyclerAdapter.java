package com.team28.borrow.presentation.feature.main.adapter;

import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseRecyclerViewAdapter;
import com.team28.borrow.presentation.model.ItemModel;

public class HomeRecyclerAdapter extends BaseRecyclerViewAdapter<ItemModel,HomeItemViewHolder> {
    @Override

    public int getItemLayoutResource() {
        return R.layout.item_home_list;
    }

    @Override
    public HomeItemViewHolder getViewHolder(View view) {
        return new HomeItemViewHolder(view);
    }
}
