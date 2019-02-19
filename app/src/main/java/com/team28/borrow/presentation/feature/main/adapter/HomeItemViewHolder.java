package com.team28.borrow.presentation.feature.main.adapter;

import android.support.v4.widget.CircularProgressDrawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseViewHolder;
import com.team28.borrow.presentation.model.ItemModel;
import com.team28.borrow.util.custom_view.MMTextView;

import butterknife.BindView;

public class HomeItemViewHolder extends BaseViewHolder<ItemModel> {


    @BindView(R.id.imgCategory)
    ImageView imgCategory;

    @BindView(R.id.txtBrandName)
    MMTextView txtBrandName;

    @BindView(R.id.txtItemName)
    MMTextView txtItemName;





    HomeItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(ItemModel itemModel) {

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(imgCategory.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(imgCategory.getContext())
                .load(itemModel.img())
                .apply(new RequestOptions().placeholder(circularProgressDrawable))
                .into(imgCategory);

        txtBrandName.setText(itemModel.brand_name());
        txtItemName.setText(itemModel.item_name());

    }
}
