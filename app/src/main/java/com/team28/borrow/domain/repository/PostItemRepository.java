package com.team28.borrow.domain.repository;

import android.support.annotation.NonNull;

import com.team28.borrow.domain.model.PostItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface PostItemRepository {
    Flowable<List<PostItem>> getItems();

    Flowable<List<PostItem>> getItemsByCategory(@NonNull String category);

    Completable addItem(PostItem item);

    //history data for current user
    Flowable<List<PostItem>> getItemsByCurrentUser(@NonNull String uuid);


    Completable updateItem(PostItem item,String id);


}
