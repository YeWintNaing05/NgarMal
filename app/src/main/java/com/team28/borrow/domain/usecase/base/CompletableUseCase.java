package com.team28.borrow.domain.usecase.base;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public abstract class CompletableUseCase<Action> {
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public CompletableUseCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    public Completable execute(Action action) {
        return executeInternal(action).subscribeOn(Schedulers.from(threadExecutor)).observeOn(postExecutionThread.getScheduler());
    }

    protected abstract Completable executeInternal(Action action);
}