package com.team28.borrow.data.datasource.noti.network;

import com.team28.borrow.data.model.entity.NotiDataEntity;
import com.team28.borrow.domain.model.Noti;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public interface NetworkNotiDataSource {

    //get noti item by currrent user
    Flowable<List<NotiDataEntity>> getNoti(@NonNull String uuid);

    Flowable<NotiDataEntity> getNotiData(@NonNull String uuid);

    //send noti to specified user id
    void sendNoti(@NonNull NotiDataEntity entity);


}
