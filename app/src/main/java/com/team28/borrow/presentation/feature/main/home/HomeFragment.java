package com.team28.borrow.presentation.feature.main.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.custom.ItemOffsetDecoration;
import com.team28.borrow.presentation.feature.detail.DetailActivity;
import com.team28.borrow.presentation.feature.item_list.CategoryItemListActivity;
import com.team28.borrow.presentation.feature.main.adapter.HomeRecyclerAdapter;
import com.team28.borrow.presentation.feature.main.adapter.ShowItemsRecyclerAdapter;
import com.team28.borrow.presentation.model.ItemModel;
import com.team28.borrow.util.custom_view.MMTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.rvOneItemList)
    RecyclerView rvOneItemList;


    @BindView(R.id.rvAllItemList)
    RecyclerView rvAllItemList;

    @BindView(R.id.rvNextOneItemList)
    RecyclerView rvNextOneItemList;

    private ShowItemsRecyclerAdapter mAllItemAdapter;
    private HomeRecyclerAdapter mOneCategoryItemAdapter, mNextOneCategoryItemAdapter;
    List<ItemModel> listOneCategoryItem = new ArrayList<>();
    List<ItemModel> listNextCategoryItem = new ArrayList<>();
    List<ItemModel> listAllCategory = new ArrayList<>();


    @Inject
    ViewModelProvider.Factory factory;


    HomeViewModel homeViewModel;

    @BindView(R.id.txtNextCategoryMore)
    TextView txtNextCategoryMore;


    @BindView(R.id.txtCategoryMore)
    TextView txtCategoryMore;

    @BindView(R.id.loadingProgress)
    ProgressBar loadingProgressBar;

    @BindView(R.id.rlAll)
    RelativeLayout rlAll;

    @BindView(R.id.linearRoot)
    LinearLayout linearRoot;

    @BindView(R.id.cvCategory)
    MaterialCardView cvCategory;

    @BindView(R.id.cvNextCategory)
    MaterialCardView cvNextCategory;

    @BindView(R.id.txtNoData)
    MMTextView txtNoData;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);


        if (savedInstanceState == null) {
            homeViewModel.getItems();
            homeViewModel.getItemsByCategory("ကား");
            homeViewModel.getItemsByNextCategory("ပန်းကန်ခွက်ယောက်များ");
        }


        //start subscription
        startSubscription();
    }

    private void init() {

        mOneCategoryItemAdapter = new HomeRecyclerAdapter();
        mNextOneCategoryItemAdapter = new HomeRecyclerAdapter();

        mOneCategoryItemAdapter.setModels(listOneCategoryItem);
        mNextOneCategoryItemAdapter.setModels(listNextCategoryItem);

        mAllItemAdapter = new ShowItemsRecyclerAdapter();
        mAllItemAdapter.setModels(listAllCategory);


        //for all category list
        rvAllItemList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvAllItemList.setHasFixedSize(true);
        rvAllItemList.addItemDecoration(new ItemOffsetDecoration(10));
        rvAllItemList.setAdapter(mAllItemAdapter);

        //for only one category
        rvOneItemList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvOneItemList.setHasFixedSize(true);
        rvOneItemList.setAdapter(mOneCategoryItemAdapter);

        //for next one category
        rvNextOneItemList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvNextOneItemList.setHasFixedSize(true);
        rvNextOneItemList.setAdapter(mNextOneCategoryItemAdapter);

    }

    private void startSubscription() {
        add(homeViewModel.getItemListStream().subscribe(this::renderWithItemAll));
        add(mOneCategoryItemAdapter.itemPositonClickStream().subscribe(integer -> changeDetail(integer, listOneCategoryItem)));
        add(mNextOneCategoryItemAdapter.itemPositonClickStream().subscribe(integer -> changeDetail(integer, listNextCategoryItem)));
        add(homeViewModel.getItemListStreamByCategory().subscribe(this::renderWithFirstCategory));
        add(homeViewModel.getItemListStreamByNextCategory().subscribe(this::renderWithNextCategory));
        add(mAllItemAdapter.itemPositonClickStream().subscribe(this::changeWithAll));
        add(RxView.clicks(txtCategoryMore).subscribe(ignored -> showMoreItem(listOneCategoryItem)));
        add(RxView.clicks(txtNextCategoryMore).subscribe(ignored -> showMoreItem(listNextCategoryItem)));

    }

    private void renderWithNextCategory(List<ItemModel> itemModels) {
        if (!itemModels.isEmpty()) {
            loadingProgressBar.setVisibility(View.GONE);
            cvNextCategory.setVisibility(View.VISIBLE);

            listNextCategoryItem = itemModels;
            mNextOneCategoryItemAdapter.setModels(itemModels);
            mNextOneCategoryItemAdapter.notifyDataSetChanged();
        }
    }

    private void renderWithFirstCategory(List<ItemModel> itemModels) {
        if (!itemModels.isEmpty()) {
            loadingProgressBar.setVisibility(View.GONE);
            cvCategory.setVisibility(View.VISIBLE);

            listOneCategoryItem = itemModels;
            mOneCategoryItemAdapter.setModels(itemModels);
            mOneCategoryItemAdapter.notifyDataSetChanged();

        }
    }

    private void changeWithAll(Integer integer) {
        DetailActivity.start(getActivity(), (ArrayList<ItemModel>) listAllCategory, integer);
    }

    private void renderWithItemAll(List<ItemModel> itemModels) {
        if (!itemModels.isEmpty()) {
            loadingProgressBar.setVisibility(View.GONE);
            rlAll.setVisibility(View.VISIBLE);

            listAllCategory = itemModels;
            mAllItemAdapter.setModels(itemModels);
            mAllItemAdapter.notifyDataSetChanged();
        } else {
            loadingProgressBar.setVisibility(View.GONE);
            linearRoot.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);

        }
    }

    private void changeDetail(int itemPos, List<ItemModel> data) {
        DetailActivity.start(getActivity(), (ArrayList<ItemModel>) data, itemPos);
    }


    private void showMoreItem(List<ItemModel> data) {
        CategoryItemListActivity.start(getContext(), (ArrayList<ItemModel>) data);
    }


}
