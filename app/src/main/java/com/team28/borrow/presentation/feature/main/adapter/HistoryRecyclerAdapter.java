package com.team28.borrow.presentation.feature.main.adapter;

import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseRecyclerViewAdapter;
import com.team28.borrow.presentation.model.ItemModel;

public class HistoryRecyclerAdapter extends BaseRecyclerViewAdapter<ItemModel,HistoryItemViewHolder> {



    @Override
    public int getItemLayoutResource() {
        return R.layout.item_history;
    }

    @Override
    public HistoryItemViewHolder getViewHolder(View view) {
        return new HistoryItemViewHolder(view);
    }
}
