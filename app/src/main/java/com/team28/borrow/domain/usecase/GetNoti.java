package com.team28.borrow.domain.usecase;


import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.Noti;
import com.team28.borrow.domain.repository.NotiRepository;
import com.team28.borrow.domain.usecase.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetNoti extends UseCase<GetNoti.Action, List<Noti>> {


    private NotiRepository notiRepository;

    @Inject
    public GetNoti(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, NotiRepository notiRepository) {
        super(threadExecutor, postExecutionThread);
        this.notiRepository = notiRepository;
    }

    @Override
    public Observable<List<Noti>> execute(GetNoti.Action action) {
        return notiRepository.getNoti(action.uuid).toObservable();
    }

    @Override
    public List<Noti> progress() {
        return new ArrayList<>();
    }

    @Override
    public List<Noti> error(Throwable throwable) {
        return null;
    }

    public static class Action {
        public String uuid;

        public Action(String uuid) {
            this.uuid = uuid;
        }
    }
}
