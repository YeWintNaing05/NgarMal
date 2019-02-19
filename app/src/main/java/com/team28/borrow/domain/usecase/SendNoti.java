package com.team28.borrow.domain.usecase;


import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.Noti;
import com.team28.borrow.domain.repository.NotiRepository;
import com.team28.borrow.domain.usecase.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class SendNoti extends CompletableUseCase<SendNoti.Action> {


    private NotiRepository notiRepository;


    @Inject
    public SendNoti(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, NotiRepository notiRepository) {
        super(threadExecutor, postExecutionThread);
        this.notiRepository = notiRepository;
    }

    @Override
    protected Completable executeInternal(Action action) {
        return notiRepository.sendNoti(action.noti);
    }

    public static class Action {
        public Noti noti;

        public Action(Noti noti) {
            this.noti = noti;
        }
    }
}
