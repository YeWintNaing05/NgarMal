package com.team28.borrow.domain.repository;

import com.team28.borrow.domain.model.BorrowForm;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public interface BorrowRepository {
    Completable applyBorrow(BorrowForm item);

    //history data for current user
    Flowable<List<BorrowForm>> getBorrowApplyByCurrentUser(@NonNull String uuid);




}
