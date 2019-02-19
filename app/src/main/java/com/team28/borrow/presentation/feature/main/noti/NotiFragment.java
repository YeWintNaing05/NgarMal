package com.team28.borrow.presentation.feature.main.noti;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.feature.main.adapter.NotiRecyclerAdapter;
import com.team28.borrow.presentation.model.NotiModel;
import com.team28.borrow.util.custom_view.MMTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;

public class NotiFragment extends BaseFragment {


    @BindView(R.id.rvNotiList)
    RecyclerView rvNotiList;

    @BindView(R.id.txtNoData)
    MMTextView txtNoData;

    private NotiRecyclerAdapter mAdapter;

    @Inject
    ViewModelProvider.Factory factory;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private NotiViewModel notiViewModel;


    public NotiFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new NotiRecyclerAdapter();
        mAdapter.setModels(new ArrayList<>());


        rvNotiList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotiList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        rvNotiList.setHasFixedSize(true);

        rvNotiList.setAdapter(mAdapter);

        notiViewModel = ViewModelProviders.of(this, factory).get(NotiViewModel.class);


        if (savedInstanceState == null)
            notiViewModel.getNoti(FirebaseAuth.getInstance().getUid());
//        prepareTestData();

        startSubscription();
    }

    private void startSubscription() {
        add(notiViewModel.notiItemStream().subscribe(this::render));

        add(mAdapter.itemClickStream().subscribe(this::goToUserDetail));

    }

    private void render(List<NotiModel> notiModels) {
        if (!notiModels.isEmpty()) {
            mAdapter.setModels(notiModels);
            progressBar.setVisibility(View.GONE);
            txtNoData.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rvNotiList.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_sub_noti;
    }


    private void goToUserDetail(NotiModel notiModel) {
        showSnack("To detail of " + notiModel.title(), R.string.btn_label_dismiss, v -> {
        }, Snackbar.LENGTH_SHORT);
    }

}
