package com.team28.borrow.presentation.feature.main.adapter;

import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseRecyclerViewAdapter;
import com.team28.borrow.presentation.model.BorrowFormModel;

public class HistoryBorrowItemRecyclerAdapter extends BaseRecyclerViewAdapter<BorrowFormModel,HistoryBorrowItemViewHolder> {
    @Override
    public int getItemLayoutResource() {
        return R.layout.item_borrow_history;
    }

    @Override
    public HistoryBorrowItemViewHolder getViewHolder(View view) {
        return new HistoryBorrowItemViewHolder(view);
    }
}
