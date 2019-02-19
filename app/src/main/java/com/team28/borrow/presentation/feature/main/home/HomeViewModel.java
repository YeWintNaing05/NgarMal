package com.team28.borrow.presentation.feature.main.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.team28.borrow.domain.usecase.GetPostItem;
import com.team28.borrow.domain.usecase.GetPostItemByCategory;
import com.team28.borrow.presentation.base.BaseViewModel;
import com.team28.borrow.presentation.mapper.ItemMapper;
import com.team28.borrow.presentation.model.ItemModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static android.content.ContentValues.TAG;

public class HomeViewModel extends BaseViewModel {

    private GetPostItem mGetPostItem;
    private GetPostItemByCategory mGetPostItemByCategory;
    private final ItemMapper itemMapper;

    private BehaviorSubject<List<ItemModel>> itemsModelSubject = BehaviorSubject.create();
    private BehaviorSubject<List<ItemModel>> itemsModelSubjectByCategory = BehaviorSubject.create();
    private BehaviorSubject<List<ItemModel>> itemsModelSubjectByNextCategory = BehaviorSubject.create();

    @Inject
    public HomeViewModel(GetPostItem getPostItem, GetPostItemByCategory mGetPostItemByCategory, ItemMapper itemMapper) {
        this.mGetPostItem = getPostItem;
        this.mGetPostItemByCategory = mGetPostItemByCategory;
        this.itemMapper = itemMapper;
    }


    void getItems() {
        add(mGetPostItem.execute(new GetPostItem.Action()).map(itemMapper::map).subscribe(itemsModelSubject::onNext, this::showError));
    }

    void getItemsByCategory(@NonNull String category) {
        add(mGetPostItemByCategory.execute(new GetPostItemByCategory.Action(category)).map(itemMapper::map).subscribe(itemsModelSubjectByCategory::onNext, this::showError));
    }

    void getItemsByNextCategory(@NonNull String category) {
        add(mGetPostItemByCategory.execute(new GetPostItemByCategory.Action(category)).map(itemMapper::map).subscribe(itemsModelSubjectByNextCategory::onNext, this::showError));
    }


    private void showError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }


    Observable<List<ItemModel>> getItemListStream() {
        return itemsModelSubject;

    }

    Observable<List<ItemModel>> getItemListStreamByCategory() {
        return itemsModelSubjectByCategory;

    }

    Observable<List<ItemModel>> getItemListStreamByNextCategory() {
        return itemsModelSubjectByNextCategory;

    }
}

