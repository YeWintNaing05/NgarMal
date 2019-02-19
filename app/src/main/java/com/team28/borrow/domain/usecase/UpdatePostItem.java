package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.PostItem;
import com.team28.borrow.domain.repository.PostItemRepository;
import com.team28.borrow.domain.usecase.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class UpdatePostItem extends CompletableUseCase<UpdatePostItem.Action> {


    private PostItemRepository postItemRepository;

    @Inject
    public UpdatePostItem(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PostItemRepository postItemRepository) {
        super(threadExecutor, postExecutionThread);
        this.postItemRepository = postItemRepository;
    }

    @Override
    protected Completable executeInternal(Action action) {
        return postItemRepository.updateItem(action.item, action.id);
    }

    public static  class Action {
        public PostItem item;
        public String id;

        public Action(PostItem item, String id) {
            this.item = item;
            this.id = id;
        }
    }
}
