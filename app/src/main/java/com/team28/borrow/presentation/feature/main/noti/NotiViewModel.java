package com.team28.borrow.presentation.feature.main.noti;

import android.util.Log;

import com.team28.borrow.domain.usecase.GetAddedNoti;
import com.team28.borrow.domain.usecase.GetNoti;
import com.team28.borrow.presentation.base.BaseViewModel;
import com.team28.borrow.presentation.mapper.NotiMapper;
import com.team28.borrow.presentation.model.NotiModel;

import java.util.List;

import javax.inject.Inject;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;

public class NotiViewModel extends BaseViewModel {

    private GetNoti mGetNoti;
    private NotiMapper mapper;

    private GetAddedNoti mGetAddedNoti;

    private BehaviorSubject<List<NotiModel>> notiItemSubject = BehaviorSubject.create();

    private BehaviorSubject<NotiModel> notiAddedItemSubject = BehaviorSubject.create();

    @Inject
     NotiViewModel(GetNoti mGetNoti, NotiMapper mapper, GetAddedNoti mGetAddedNoti) {
        this.mGetNoti = mGetNoti;
        this.mapper = mapper;
        this.mGetAddedNoti = mGetAddedNoti;
    }


    public void getAddedNoti(@NonNull String uuid) {
        add(mGetAddedNoti.execute(new GetAddedNoti.Action(uuid)).map(mapper::map).subscribe(notiAddedItemSubject::onNext, this::showError));
    }

    public void getNoti(@NonNull String uuid) {
        add(mGetNoti.execute(new GetNoti.Action(uuid)).map(mapper::map).subscribe(notiItemSubject::onNext, this::showError));
    }

    private void showError(Throwable throwable) {
        Log.e("noti error", throwable.getMessage());
    }

    public Observable<List<NotiModel>> notiItemStream() {
        return notiItemSubject;
    }

    public Observable<NotiModel> notiAddedItemStream() {
        return notiAddedItemSubject;
    }
}
