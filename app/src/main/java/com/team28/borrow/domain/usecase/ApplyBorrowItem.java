package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.BorrowForm;
import com.team28.borrow.domain.repository.BorrowRepository;
import com.team28.borrow.domain.usecase.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ApplyBorrowItem extends CompletableUseCase<ApplyBorrowItem.Action> {


    private final BorrowRepository mBorrowRepository;

    @Inject
    ApplyBorrowItem(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, BorrowRepository borrowRepository) {
        super(threadExecutor, postExecutionThread);
        this.mBorrowRepository = borrowRepository;
    }

    @Override
    protected Completable executeInternal(Action action) {
        return mBorrowRepository.applyBorrow(action.borrowForm);
    }

    public static class Action {
        private final BorrowForm borrowForm;

        public Action(BorrowForm borrowForm) {
            this.borrowForm = borrowForm;
        }


    }

}