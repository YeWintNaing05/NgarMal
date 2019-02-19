package com.team28.borrow.presentation.feature.main.adapter;

import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseRecyclerViewAdapter;
import com.team28.borrow.presentation.model.CategoryModel;

public class CategoryRecyclerAdapter extends BaseRecyclerViewAdapter<CategoryModel,CategoryViewHolder> {


    @Override
    public int getItemLayoutResource() {
        return R.layout.item_category;
    }

    @Override
    public CategoryViewHolder getViewHolder(View view) {
        return new CategoryViewHolder(view);
    }
}
