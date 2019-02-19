package com.team28.borrow.presentation.feature.main.adapter;

import android.support.v4.widget.CircularProgressDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseViewHolder;
import com.team28.borrow.presentation.model.ItemModel;
import com.team28.borrow.util.Constants;
import com.team28.borrow.util.custom_view.MMTextView;

import butterknife.BindView;

public class HistoryItemViewHolder extends BaseViewHolder<ItemModel> {

    @BindView(R.id.imgItem)
    ImageView imgItem;

    @BindView(R.id.txtItem)
    TextView txtItem;

    @BindView(R.id.txtBrandName)
    TextView txtBrandName;

    @BindView(R.id.txtItemState)
    MMTextView txtItemState;


    public HistoryItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(ItemModel itemModel) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(imgItem.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(imgItem.getContext())
                .load(itemModel.img())
                .apply(new RequestOptions().placeholder(circularProgressDrawable))
                .into(imgItem);


        txtItem.setText(itemModel.item_name());
        switch (itemModel.state()) {
            case Constants.PENDING:
                txtItemState.setText(txtItemState.getContext().getString(R.string.pending));
                txtItemState.setBackgroundColor(txtItemState.getContext().getResources().getColor(android.R.color.holo_blue_light));
                break;
            case Constants.REJECT:
                txtItemState.setText(txtItemState.getContext().getString(R.string.reject));
                txtItemState.setBackgroundColor(txtItemState.getContext().getResources().getColor(android.R.color.holo_red_light));
                break;
            default:
                txtItemState.setText(txtItemState.getContext().getString(R.string.accept_owner));
                txtItemState.setBackgroundColor(txtItemState.getContext().getResources().getColor(android.R.color.holo_green_light));
                break;
        }

        txtBrandName.setText(itemModel.brand_name());


        //  txtPricePer.setText(String.format("%d per%d   %s", itemModel.price(), itemModel.count(), itemModel.date()));

    }


}
