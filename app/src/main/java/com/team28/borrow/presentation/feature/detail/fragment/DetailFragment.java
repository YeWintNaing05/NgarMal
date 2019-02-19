package com.team28.borrow.presentation.feature.detail.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.feature.borrow.BorrowFormActivity;
import com.team28.borrow.presentation.model.ItemModel;
import com.team28.borrow.util.Utils;
import com.team28.borrow.util.custom_view.MMButtonView;
import com.team28.borrow.util.custom_view.MMTextView;

import java.util.Objects;

import butterknife.BindView;


public class DetailFragment extends BaseFragment {


    @BindView(R.id.txtModelName)
    MMTextView txtTitle;

    @BindView(R.id.txtBrandName)
    MMTextView txtBrandName;


    @BindView(R.id.txtCarEngine)
    MMTextView txtCarEngine;

    @BindView(R.id.txtGasOrCar)
    MMTextView txtGasOrCar;

    @BindView(R.id.txtCameraZoom)
    MMTextView txtCameraZoom;

    @BindView(R.id.txtFocus)
    MMTextView txtFocus;

    @BindView(R.id.cvCar)
    MaterialCardView cvCar;

    @BindView(R.id.cvCamera)
    MaterialCardView cvCamera;

    @BindView(R.id.txtSeat)
    MMTextView txtSeat;

    @BindView(R.id.imgItem)
    ImageView imgItem;

    @BindView(R.id.btnBorrow)
    MMButtonView btnBorrow;

    @BindView(R.id.txtPricePerDay)
    MMTextView txtPricePerDay;

    @BindView(R.id.txtPricePerWeek)
    MMTextView txtPricePerWeek;

    @BindView(R.id.txtPricePerMonth)
    MMTextView txtPricePerMonth;

    @BindView(R.id.txtDamage)
    MMTextView txtDamage;


    private String itemPrice, itemDateCount;

    private ItemModel mItemModel;


    public DetailFragment() {
        // Required empty public constructo`r
    }

    public static DetailFragment newInstance(ItemModel data) {

        Bundle args = new Bundle();
        args.putParcelable("data", data);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemModel = getArguments().getParcelable("data");
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTitle.setText(mItemModel.item_name());
        txtBrandName.setText(mItemModel.brand_name());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(Objects.requireNonNull(getActivity()));
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(this)
                .load(mItemModel.img())
                .apply(new RequestOptions().placeholder(circularProgressDrawable))
                .into(imgItem);


        if (mItemModel.price_per_day() == 0) {
            txtPricePerDay.setVisibility(View.GONE);
        } else {
            txtPricePerDay.setText(String.format("%s %d", getString(R.string.price_per_day), mItemModel.price_per_day()));
        }


        if (mItemModel.price_per_week() == 0) {
            txtPricePerWeek.setVisibility(View.GONE);
        } else {
            txtPricePerWeek.setText(String.format("%s %d", getString(R.string.price_per_week), mItemModel.price_per_week()));
        }

        if (mItemModel.price_per_month() == 0) {
            txtPricePerMonth.setVisibility(View.GONE);
        } else {
            txtPricePerMonth.setText(String.format("%s %d", getString(R.string.price_per_month), mItemModel.price_per_month()));
        }


        txtDamage.setText(mItemModel.damage());

        if (mItemModel.category().equals(getString(R.string.car))) {
            cvCar.setVisibility(View.VISIBLE);


            txtCarEngine.setText(String.format("%s%s", mItemModel.engine_power(), getString(R.string.engine_)));
            txtGasOrCar.setText(mItemModel.gas_or_oil());
            txtSeat.setText(String.format("%s ခုံပါသည်", mItemModel.seat_num()));
        }

        if (mItemModel.category().equals(getString(R.string.camera))) {
            cvCamera.setVisibility(View.VISIBLE);

            txtCameraZoom.setText(mItemModel.zoom());
            txtFocus.setText(String.format("Camera Focus %s", mItemModel.focus_condition()));
        }

        if (Utils.isPostOwner(mItemModel.user_id())) {
            btnBorrow.setText("သင်သည် ပစ္စည်းပိုင်ရှင်ဖြစ်ပါသည်");
            btnBorrow.setEnabled(false);
        }

        startSubscription();
    }

    private void startSubscription() {
        add(RxView.clicks(btnBorrow).subscribe(ignored -> borrow()));
        add(RxView.clicks(imgItem).subscribe(ignored -> {
            showZoomImage();
        }));
    }


    private void showZoomImage() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.zoom_image);

        ImageView imageView = dialog.findViewById(R.id.imgZoom);
        Glide.with(dialog.getContext())
                .load(mItemModel.img())
                .into(imageView);


        dialog.show();


    }


    private void borrow() {
        BorrowFormActivity.start(getActivity(), mItemModel);
    }
}
