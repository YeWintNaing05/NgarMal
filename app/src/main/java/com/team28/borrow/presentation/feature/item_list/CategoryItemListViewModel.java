package com.team28.borrow.presentation.feature.item_list;

import android.util.Log;

import com.team28.borrow.domain.usecase.GetPostItemByCategory;
import com.team28.borrow.presentation.base.BaseViewModel;
import com.team28.borrow.presentation.mapper.ItemMapper;
import com.team28.borrow.presentation.model.ItemModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;

import static android.content.ContentValues.TAG;


public class CategoryItemListViewModel extends BaseViewModel {

    private GetPostItemByCategory mGetPostItemByCategory;
    private final ItemMapper itemMapper;

    private BehaviorSubject<List<ItemModel>> itemsModelSubject = BehaviorSubject.create();

    @Inject
    CategoryItemListViewModel(GetPostItemByCategory mGetPostItemByCategory, ItemMapper itemMapper) {
        this.mGetPostItemByCategory = mGetPostItemByCategory;
        this.itemMapper = itemMapper;
    }


    void getItemsByCategory(@NonNull String category) {
        add(mGetPostItemByCategory.execute(new GetPostItemByCategory.Action(category)).map(itemMapper::map).subscribe(itemsModelSubject::onNext, this::showError));
    }


    private void showError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }


    Observable<List<ItemModel>> getItemListStream() {
        return itemsModelSubject;

    }


}
