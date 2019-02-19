package com.team28.borrow.data.repository;

import android.support.annotation.NonNull;

import com.team28.borrow.data.datasource.item.mapper.ItemEntityMapper;
import com.team28.borrow.data.datasource.item.network.NetworkItemDataSource;
import com.team28.borrow.domain.model.PostItem;
import com.team28.borrow.domain.repository.PostItemRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class PostItemRepositoryImpl implements PostItemRepository {


    private final NetworkItemDataSource mNetworkItemDataSource;
    private final ItemEntityMapper mItemEntityMapper;


    @Inject
    PostItemRepositoryImpl(NetworkItemDataSource mNetworkItemDataSource, ItemEntityMapper mItemEntityMapper) {
        this.mNetworkItemDataSource = mNetworkItemDataSource;
        this.mItemEntityMapper = mItemEntityMapper;
    }


    @Override
    public Flowable<List<PostItem>> getItems() {
        return mNetworkItemDataSource.getItemEntities().map(mItemEntityMapper::map);
    }

    @Override
    public Flowable<List<PostItem>> getItemsByCategory(@NonNull String category) {
        return mNetworkItemDataSource.getItemEntitiesByCategory(category).map(mItemEntityMapper::map);
    }

    @Override
    public Completable addItem(PostItem item) {
        return Completable.fromAction(() -> mNetworkItemDataSource.addItem(mItemEntityMapper.reverse(item)));
    }

    @Override
    public Flowable<List<PostItem>> getItemsByCurrentUser(@NonNull String uuid) {
        return mNetworkItemDataSource.getItemEntitiesByCurrentCuser(uuid).map(mItemEntityMapper::map);
    }

    @Override
    public Completable updateItem(PostItem item, String id) {
        return Completable.fromAction(() -> mNetworkItemDataSource.updateItem(mItemEntityMapper.reverse(item), id));
    }


}
