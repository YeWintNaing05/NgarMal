package com.team28.borrow.presentation.feature.item_list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.custom.ItemOffsetDecoration;
import com.team28.borrow.presentation.feature.detail.DetailActivity;
import com.team28.borrow.presentation.feature.main.adapter.ShowItemsRecyclerAdapter;
import com.team28.borrow.presentation.model.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryItemListActivity extends BaseActivity {


    @BindView(R.id.rvItemList)
    RecyclerView rvItemList;

    @BindView(R.id.edtSearchView)
    EditText edtSearchView;

    private List<ItemModel> modelList = new ArrayList<>();

    private ShowItemsRecyclerAdapter mAdapter;

    @Inject
    ViewModelProvider.Factory factory;


    CategoryItemListViewModel categoryItemListViewModel;

    public static void start(Context context, ArrayList<ItemModel> data) {
        Intent starter = new Intent(context, CategoryItemListActivity.class);
        starter.putParcelableArrayListExtra("data", data);
        context.startActivity(starter);
    }

    public static void start(Context context, String data) {
        Intent starter = new Intent(context, CategoryItemListActivity.class);
        starter.putExtra("category", data);
        context.startActivity(starter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void main(Bundle savedInstanceState) {
        mAdapter = new ShowItemsRecyclerAdapter();

//        hideSoftKeyboard(this);
//        edtSearchView.setShowSoftInputOnFocus(false);

//        edtSearchView.setShowSoftInputOnFocus(false);

        Toolbar toolbar = findViewById(R.id.toolbar);


        //   toolbar.setTitle(getIntent().getStringExtra("category"));

        setSupportActionBar(toolbar);

        edtSearchView.setInputType(InputType.TYPE_NULL);


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryItemListViewModel = ViewModelProviders.of(this, factory).get(CategoryItemListViewModel.class);

        if (getIntent().hasExtra("data"))
            modelList = getIntent().getParcelableArrayListExtra("data");

        if (getIntent().hasExtra("category") && savedInstanceState == null) {
            categoryItemListViewModel.getItemsByCategory(getIntent().getStringExtra("category"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setTransitionName(getIntent().getStringExtra("category"));
            }
        }


        mAdapter.setModels(modelList);
        mAdapter.setItemList(modelList);

        rvItemList.setLayoutManager(new GridLayoutManager(this, 2));
        rvItemList.setHasFixedSize(true);
        rvItemList.addItemDecoration(new ItemOffsetDecoration(7));
        rvItemList.setAdapter(mAdapter);


        startSubscription();

    }

    private void startSubscription() {
        add(mAdapter.itemPositonClickStream().subscribe(integer -> changeDetail(integer, modelList)));
        add(categoryItemListViewModel.getItemListStream().subscribe(this::render));
        add(RxTextView.textChangeEvents(edtSearchView)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewTextChangeEvent -> mAdapter.getFilter().filter(textViewTextChangeEvent.text()))
        );
        add(RxView.touches(edtSearchView, motionEvent -> motionEvent.getAction() == MotionEvent.ACTION_UP).subscribe(
                ignored -> {
                    edtSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
                    edtSearchView.requestFocus();
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.showSoftInput(edtSearchView, InputMethodManager.SHOW_FORCED);
                }
        ));
    }


    private void render(List<ItemModel> itemModels) {
        this.modelList = itemModels;
        mAdapter.setModels(modelList);
        mAdapter.setItemList(itemModels);
        mAdapter.notifyDataSetChanged();
    }


    private void changeDetail(int itemPos, List<ItemModel> data) {
        DetailActivity.start(this, (ArrayList<ItemModel>) data, itemPos);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_item_list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
}
