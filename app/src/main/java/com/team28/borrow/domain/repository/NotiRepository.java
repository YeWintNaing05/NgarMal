package com.team28.borrow.domain.repository;


import com.team28.borrow.domain.model.Noti;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public interface NotiRepository {

    //get all noti by current user
    Flowable<List<Noti>> getNoti(@NonNull String uuid);

    //get added data

    Flowable<Noti> getNotiData(@NonNull String uuid);

    //send noti to specified user id
    Completable sendNoti(@NonNull Noti noti);
}
