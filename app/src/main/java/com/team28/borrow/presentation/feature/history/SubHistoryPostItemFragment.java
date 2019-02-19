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
import com.team28.borrow.presentation.feature.detail.DetailActivity;
import com.team28.borrow.presentation.feature.main.adapter.HistoryRecyclerAdapter;
import com.team28.borrow.presentation.feature.owner_post.OwnerFormActivity;
import com.team28.borrow.presentation.model.ItemModel;
import com.team28.borrow.util.Constants;
import com.team28.borrow.util.custom_view.MMTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SubHistoryPostItemFragment extends BaseFragment {


    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;

    private HistoryRecyclerAdapter mAdapter;
    private List<ItemModel> itemModelList = new ArrayList<>();

    @Inject
    ViewModelProvider.Factory factory;

    HistoryViewModel historyViewModel;

    @BindView(R.id.loadingProgress)
    ProgressBar loadingProgress;

    @BindView(R.id.txtNoData)
    MMTextView txtNoData;


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_sub_history;
    }

    private void startSubscription() {
        add(historyViewModel.getItemListStream().subscribe(this::render));
        add(mAdapter.itemPositonClickStream().subscribe(this::changeDetail));
//        add(mAdapter.itemClickStream().subscribe(this::changeForm));
    }

    /* private void changeForm(ItemModel itemModel) {
         if (itemModel.state().equals(Constants.REJECT)) {
         }
     }
 */
    private void changeDetail(Integer integer) {
        if (itemModelList.get(integer).state().equals(Constants.REJECT)) {
            OwnerFormActivity.start(getContext(), itemModelList.get(integer));

        } else {
            DetailActivity.start(getActivity(), (ArrayList<ItemModel>) itemModelList, integer);
        }
    }

    private void render(List<ItemModel> itemModels) {
        if (!itemModels.isEmpty()) {
            loadingProgress.setVisibility(View.GONE);
            itemModelList = itemModels;
            mAdapter.setModels(itemModelList);
            mAdapter.notifyDataSetChanged();
            rvHistory.setVisibility(View.VISIBLE);
        } else {
            loadingProgress.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new HistoryRecyclerAdapter();
        mAdapter.setModels(itemModelList);

        rvHistory.addItemDecoration(new ItemOffsetDecoration(5));
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHistory.setHasFixedSize(true);

        rvHistory.setAdapter(mAdapter);


        historyViewModel = ViewModelProviders.of(this, factory).get(HistoryViewModel.class);

        if (savedInstanceState == null)
            historyViewModel.getHistoryItems();


        startSubscription();

    }


}
