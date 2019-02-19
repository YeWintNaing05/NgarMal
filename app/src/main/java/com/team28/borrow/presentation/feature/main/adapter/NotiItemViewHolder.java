package com.team28.borrow.presentation.feature.main.adapter;

import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseViewHolder;
import com.team28.borrow.presentation.model.NotiModel;
import com.team28.borrow.util.Constants;
import com.team28.borrow.util.custom_view.MMTextView;

import butterknife.BindView;

public class NotiItemViewHolder extends BaseViewHolder<NotiModel> {

    @BindView(R.id.txtDate)
    MMTextView txtDate;

    @BindView(R.id.txtConfirmType)
    MMTextView txtConfirmType;

    @BindView(R.id.txtContent)
    MMTextView txtContent;


    NotiItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(NotiModel notiModel) {
        txtDate.setText(notiModel.date());

        if (notiModel.title().equals(Constants.ACCEPT)) {
            txtConfirmType.setTextColor(txtConfirmType.getContext().getResources().getColor(android.R.color.holo_green_light));
            txtConfirmType.setText(txtConfirmType.getContext().getString(R.string.accept));
        } else {
            txtConfirmType.setTextColor(txtConfirmType.getContext().getResources().getColor(android.R.color.holo_red_light));
            txtConfirmType.setText(txtConfirmType.getContext().getString(R.string.reject));
        }
        txtContent.setText(notiModel.content());
    }
}
