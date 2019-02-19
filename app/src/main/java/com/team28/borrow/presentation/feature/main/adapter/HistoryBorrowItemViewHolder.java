package com.team28.borrow.presentation.feature.main.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseViewHolder;
import com.team28.borrow.presentation.model.BorrowFormModel;
import com.team28.borrow.util.Constants;
import com.team28.borrow.util.custom_view.MMTextView;

import butterknife.BindView;

public class HistoryBorrowItemViewHolder extends BaseViewHolder<BorrowFormModel> {


    @BindView(R.id.imgItem)
    ImageView imgItem;

    @BindView(R.id.txtItemState)
    MMTextView txtItemState;

    @BindView(R.id.txtItemModelTitle)
    MMTextView txtItemModelTitle;

    @BindView(R.id.txtBrandName)
    MMTextView txtBrandName;

    @BindView(R.id.txtBorrowDateNumber)
    MMTextView txtBorrowDateNumber;

    @BindView(R.id.txtBorrowFee)
    MMTextView txtBorrowFee;


    HistoryBorrowItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(BorrowFormModel borrowFormModel) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(imgItem.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(imgItem.getContext())
                .load(borrowFormModel.img())
                .apply(new RequestOptions().placeholder(circularProgressDrawable))
                .into(imgItem);

        switch (borrowFormModel.state()) {
            case Constants.PENDING:
                txtItemState.setText(txtItemState.getContext().getString(R.string.pending));
                txtItemState.setBackgroundColor(txtItemState.getContext().getResources().getColor(android.R.color.holo_blue_light));
                break;
            case Constants.REJECT:
                txtItemState.setText(txtItemState.getContext().getString(R.string.reject));
                txtItemState.setBackgroundColor(txtItemState.getContext().getResources().getColor(android.R.color.holo_red_light));
                break;
            default:
                txtItemState.setText(txtItemState.getContext().getString(R.string.accept_borrower));
                txtItemState.setBackgroundColor(txtItemState.getContext().getResources().getColor(android.R.color.holo_green_light));
                break;
        }


        txtItemModelTitle.setText(borrowFormModel.item_name());
        txtBrandName.setText(borrowFormModel.item_brand());

        txtBorrowFee.setText(String.format("%s  %s", txtBorrowFee.getContext().getString(R.string.borrow_fee), borrowFormModel.fee()));
        txtBorrowDateNumber.setText(String.format("%s  %s %s", txtBorrowDateNumber.getContext().getString(R.string.borrow_number), borrowFormModel.number_of_borrow_day(), borrowFormModel.date()));
    }
}
