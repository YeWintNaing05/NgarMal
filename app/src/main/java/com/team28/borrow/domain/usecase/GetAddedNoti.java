package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.Noti;
import com.team28.borrow.domain.repository.NotiRepository;
import com.team28.borrow.domain.usecase.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetAddedNoti extends UseCase<GetAddedNoti.Action, Noti> {

    private NotiRepository notiRepository;

    @Inject
    GetAddedNoti(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, NotiRepository notiRepository) {
        super(threadExecutor, postExecutionThread);
        this.notiRepository = notiRepository;
    }

    @Override
    public Observable<Noti> execute(GetAddedNoti.Action action) {
        return notiRepository.getNotiData(action.uid).toObservable();
    }

    @Override
    public Noti progress() {
        return Noti.progress();
    }

    @Override
    public Noti error(Throwable throwable) {
        return null;
    }

    public static class Action {
        public String uid;

        public Action(String uid) {
            this.uid = uid;
        }
    }
}
