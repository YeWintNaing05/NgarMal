package com.team28.borrow.data.datasource.borrow_form_data.network;

import com.team28.borrow.data.model.entity.BorrowFormDataEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public interface NetworkBorrowFormDataSource {

    //add item
    void applyBorrowForm(BorrowFormDataEntity borrowFormDataEntity);

    //history data for current user
    Flowable<List<BorrowFormDataEntity>> getBorrowApplyByCurrentUser(@NonNull String uuid);



}
