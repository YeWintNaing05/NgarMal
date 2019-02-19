package com.team28.borrow.presentation.feature.main.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.custom.ItemOffsetDecoration;
import com.team28.borrow.presentation.feature.item_list.CategoryItemListActivity;
import com.team28.borrow.presentation.feature.main.adapter.CategoryRecyclerAdapter;
import com.team28.borrow.presentation.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;


public class CategoryFragment extends BaseFragment {


    @BindView(R.id.rvCategoryLIst)
    RecyclerView rvCategoryList;

    private CategoryRecyclerAdapter mAdapter;


    public CategoryFragment() {
        // Required empty public constructor
    }


    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_category;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<CategoryModel> data = new ArrayList<>();
        data.add(CategoryModel.builder().img(R.drawable.ic_car).name("ကား").build());
        data.add(CategoryModel.builder().img(R.drawable.ic_electronic).name("လျှပ်စစ်ပစ္စည်းများ").build());
        data.add(CategoryModel.builder().img(R.drawable.ic_furniture).name("ခုံများ").build());
        data.add(CategoryModel.builder().img(R.drawable.ic_camera).name("Camera And Accessories").build());
        data.add(CategoryModel.builder().img(R.drawable.food).name("ပန်းကန်ခွက်ယောက်များ").build());
        data.add(CategoryModel.builder().img(R.drawable.ic_others).name("Others").build());

        mAdapter = new CategoryRecyclerAdapter();
        mAdapter.setModels(data);


        rvCategoryList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvCategoryList.addItemDecoration(new ItemOffsetDecoration(10));
        rvCategoryList.setHasFixedSize(true);
        rvCategoryList.setAdapter(mAdapter);


        startSubscription();


    }

    private void startSubscription() {
        add(mAdapter.itemClickStream().subscribe(this::change));

    }

    private void change(CategoryModel categoryModel) {
        CategoryItemListActivity.start(getContext(), categoryModel.name());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
