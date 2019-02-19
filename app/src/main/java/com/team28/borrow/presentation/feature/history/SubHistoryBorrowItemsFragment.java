package com.team28.borrow.presentation.feature.history;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.custom.ItemOffsetDecoration;
import com.team28.borrow.presentation.feature.main.adapter.HistoryBorrowItemRecyclerAdapter;
import com.team28.borrow.presentation.model.BorrowFormModel;
import com.team28.borrow.util.custom_view.MMTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class SubHistoryBorrowItemsFragment extends BaseFragment {

    @Inject
    ViewModelProvider.Factory factory;

    HistoryViewModel historyViewModel;


    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;


    @BindView(R.id.loadingProgress)
    ProgressBar loadingProgress;

    @BindView(R.id.txtNoData)
    MMTextView txtNoData;

    private HistoryBorrowItemRecyclerAdapter mAdapter;

    public SubHistoryBorrowItemsFragment() {
        // Required empty public constructor
    }


    public static SubHistoryBorrowItemsFragment newInstance() {
        SubHistoryBorrowItemsFragment fragment = new SubHistoryBorrowItemsFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mAdapter = new HistoryBorrowItemRecyclerAdapter();
        mAdapter.setModels(new ArrayList<>());
        rvHistory.setAdapter(mAdapter);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHistory.addItemDecoration(new ItemOffsetDecoration(5));

        historyViewModel = ViewModelProviders.of(this, factory).get(HistoryViewModel.class);

        if (savedInstanceState == null)
            historyViewModel.getHistoryBorrowItems();


        startSubscription();
    }

    private void startSubscription() {
        add(historyViewModel.getBorrowDataListStream().subscribe(this::render));
    }

    private void render(List<BorrowFormModel> borrowFormModels) {
       if(!borrowFormModels.isEmpty()) {
           mAdapter.setModels(borrowFormModels);
           mAdapter.notifyDataSetChanged();
           loadingProgress.setVisibility(View.GONE);
           rvHistory.setVisibility(View.VISIBLE);
       }
        else {
            loadingProgress.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
            txtNoData.setText(getString(R.string.borrow_no_data));
            rvHistory.setVisibility(View.GONE);

        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_sub_history_borrow_items;
    }


}
