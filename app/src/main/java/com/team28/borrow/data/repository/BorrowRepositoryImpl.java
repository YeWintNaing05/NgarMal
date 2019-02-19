package com.team28.borrow.data.repository;

import com.team28.borrow.data.datasource.borrow_form_data.mapper.BorrowFormDataEntityMapper;
import com.team28.borrow.data.datasource.borrow_form_data.network.NetworkBorrowFormDataSource;
import com.team28.borrow.domain.model.BorrowForm;
import com.team28.borrow.domain.repository.BorrowRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class BorrowRepositoryImpl implements BorrowRepository {

    private final NetworkBorrowFormDataSource mNetworkBorrowFormDataSource;
    private final BorrowFormDataEntityMapper mBorrowFormDataEntityMapper;

    @Inject
    BorrowRepositoryImpl(NetworkBorrowFormDataSource mNetworkBorrowFormDataSource, BorrowFormDataEntityMapper mBorrowFormDataEntityMapper) {
        this.mNetworkBorrowFormDataSource = mNetworkBorrowFormDataSource;
        this.mBorrowFormDataEntityMapper = mBorrowFormDataEntityMapper;
    }


    @Override
    public Completable applyBorrow(BorrowForm item) {
        return Completable.fromAction(() -> mNetworkBorrowFormDataSource.applyBorrowForm(mBorrowFormDataEntityMapper.reverse(item)));
    }

    @Override
    public Flowable<List<BorrowForm>> getBorrowApplyByCurrentUser(String uuid) {
        return mNetworkBorrowFormDataSource.getBorrowApplyByCurrentUser(uuid).map(mBorrowFormDataEntityMapper::map);
    }
}
