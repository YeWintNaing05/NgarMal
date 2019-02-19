package com.team28.borrow.presentation.feature.main.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseViewHolder;
import com.team28.borrow.presentation.model.CategoryModel;
import com.team28.borrow.util.custom_view.MMTextView;

import butterknife.BindView;

public class CategoryViewHolder extends BaseViewHolder<CategoryModel> {


    @BindView(R.id.txtCategoryName)
    MMTextView txtCategoryName;

    @BindView(R.id.imgCategory)
    ImageView imgCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(CategoryModel categoryModel) {
        imgCategory.setImageResource(categoryModel.img());
        txtCategoryName.setText(categoryModel.name());


    }
}
