package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.BorrowForm;
import com.team28.borrow.domain.repository.BorrowRepository;
import com.team28.borrow.domain.usecase.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetBorrowItems extends UseCase<GetBorrowItems.Action, List<BorrowForm>> {


    private final BorrowRepository mBorrowRepository;


    @Inject
    public GetBorrowItems(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, BorrowRepository borrowRepository) {
        super(threadExecutor, postExecutionThread);
        this.mBorrowRepository = borrowRepository;
    }

    @Override
    public Observable<List<BorrowForm>> execute(GetBorrowItems.Action action) {
        return mBorrowRepository.getBorrowApplyByCurrentUser(action.uuid).toObservable();
    }

    @Override
    public List<BorrowForm> progress() {
        return new ArrayList<BorrowForm>();
    }

    @Override
    public List<BorrowForm> error(Throwable throwable) {

        return null;
    }


    public static class Action {
        public String uuid;

        public Action(String uuid) {
            this.uuid = uuid;
        }
    }

}
