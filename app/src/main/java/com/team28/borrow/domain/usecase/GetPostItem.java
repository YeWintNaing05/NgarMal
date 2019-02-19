package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.PostItem;
import com.team28.borrow.domain.repository.PostItemRepository;
import com.team28.borrow.domain.usecase.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetPostItem extends UseCase<GetPostItem.Action, List<PostItem>> {


    private final PostItemRepository mPostItemRepository;


    @Inject
    public GetPostItem(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PostItemRepository mPostItemRepository) {
        super(threadExecutor, postExecutionThread);
        this.mPostItemRepository = mPostItemRepository;
    }

    @Override
    public Observable<List<PostItem>> execute(Action action) {
        return mPostItemRepository.getItems().toObservable();
    }

    @Override
    public List<PostItem> progress() {
        return new ArrayList<PostItem>();
    }

    @Override
    public List<PostItem> error(Throwable throwable) {

        return null;
    }

    public static class Action {
    }
}
