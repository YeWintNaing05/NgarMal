package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.Category;
import com.team28.borrow.domain.repository.CategoryRepository;
import com.team28.borrow.domain.usecase.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddCategory extends CompletableUseCase<AddCategory.Action> {


    private final CategoryRepository mCategoryRepository;

    @Inject
    AddCategory(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CategoryRepository categoryRepository) {
        super(threadExecutor, postExecutionThread);
        this.mCategoryRepository = categoryRepository;
    }

    @Override
    protected Completable executeInternal(Action action) {
        return mCategoryRepository.addCategory(action.category);
    }

    public class Action {
        private final Category category;

        public Action(Category category) {
            this.category = category;
        }


    }
}
