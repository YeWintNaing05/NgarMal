package com.team28.borrow.presentation.feature.owner_post;

import android.util.Log;

import com.team28.borrow.domain.usecase.AddPostItem;
import com.team28.borrow.domain.usecase.UpdatePostItem;
import com.team28.borrow.presentation.base.BaseViewModel;
import com.team28.borrow.presentation.mapper.ItemMapper;
import com.team28.borrow.presentation.model.ItemModel;


import javax.inject.Inject;

import static android.content.ContentValues.TAG;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class OwnerFormViewModel extends BaseViewModel {

    private AddPostItem addPostItem;
    private final ItemMapper itemMapper;


    private UpdatePostItem updatePostItem;


    private BehaviorSubject<String> itemsModelSubject = BehaviorSubject.create();


    @Inject
    OwnerFormViewModel(AddPostItem addPostItem, ItemMapper itemMapper, UpdatePostItem updatePostItem) {
        this.addPostItem = addPostItem;
        this.itemMapper = itemMapper;
        this.updatePostItem = updatePostItem;
    }

    void addPostItem(ItemModel model) {
        AddPostItem.Action action = new AddPostItem.Action(itemMapper.reverse(model));
        add(addPostItem.execute(action).subscribe(() -> itemsModelSubject.onNext("Post Successfully"), this::showError));
    }

    void updatePostItem(ItemModel model, String id) {
        UpdatePostItem.Action action = new UpdatePostItem.Action(itemMapper.reverse(model), id);
        add(updatePostItem.execute(action).subscribe(() -> itemsModelSubject.onNext("Update Successfully"), this::showError));
    }


    private void showError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }


    Observable<String> getItemStream() {
        return itemsModelSubject;
    }


}
