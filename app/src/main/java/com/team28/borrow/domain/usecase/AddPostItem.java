package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.PostItem;
import com.team28.borrow.domain.repository.PostItemRepository;
import com.team28.borrow.domain.usecase.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddPostItem extends CompletableUseCase<AddPostItem.Action> {

    private final PostItemRepository mPostItemRepository;

    @Inject
    AddPostItem(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PostItemRepository postItemRepository) {
        super(threadExecutor, postExecutionThread);
        this.mPostItemRepository = postItemRepository;
    }

    @Override
    protected Completable executeInternal(Action action) {
        return mPostItemRepository.addItem(action.postItem);
    }


    public static class Action {
        private final PostItem postItem;

        public Action(PostItem postItem) {
            this.postItem = postItem;
        }
    }
}