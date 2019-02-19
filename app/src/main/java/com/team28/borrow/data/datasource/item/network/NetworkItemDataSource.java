package com.team28.borrow.data.datasource.item.network;

import android.support.annotation.NonNull;

import com.team28.borrow.data.model.entity.ItemDataEntity;


import java.util.List;

import io.reactivex.Flowable;

public interface NetworkItemDataSource {

    //get all item which is approved by admin
    Flowable<List<ItemDataEntity>> getItemEntities();


    Flowable<List<ItemDataEntity>> getItemEntitiesByCategory(@NonNull String category);


    //add item
    void addItem(ItemDataEntity itemDataEntity);

    //update item
    void updateItem(ItemDataEntity itemDataEntity,String id);

    //history data for current user
    Flowable<List<ItemDataEntity>> getItemEntitiesByCurrentCuser(@NonNull String uuid);

    //history data item by borrow item id
//    Single<List<PostItem>> getItemsByIdFromBorrow(@NonNull String borrrow_id,@NonNull String uuid);

}
