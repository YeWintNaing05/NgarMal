package com.team28.borrow.data.repository;


import com.team28.borrow.data.datasource.noti.mapper.NotiDataEntityMapper;
import com.team28.borrow.data.datasource.noti.network.NetworkNotiDataSource;
import com.team28.borrow.domain.model.Noti;
import com.team28.borrow.domain.repository.NotiRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class NotiRepositoryImpl implements NotiRepository {

    private final NetworkNotiDataSource mNetworkNotiDataSource;
    private final NotiDataEntityMapper mNotiDataEntityMapper;

    @Inject
    NotiRepositoryImpl(NetworkNotiDataSource mNetworkNotiDataSource, NotiDataEntityMapper mNotiDataEntityMapper) {
        this.mNetworkNotiDataSource = mNetworkNotiDataSource;
        this.mNotiDataEntityMapper = mNotiDataEntityMapper;
    }


    @Override
    public Flowable<List<Noti>> getNoti(String uuid) {
        return mNetworkNotiDataSource.getNoti(uuid).map(mNotiDataEntityMapper::map);
    }

    @Override
    public Flowable<Noti> getNotiData(String uuid) {
        return mNetworkNotiDataSource.getNotiData(uuid).map(mNotiDataEntityMapper::map);
    }

    @Override
    public Completable sendNoti(Noti noti) {
        return Completable.fromAction(() -> mNetworkNotiDataSource.sendNoti(mNotiDataEntityMapper.reverse(noti)));
    }
}
