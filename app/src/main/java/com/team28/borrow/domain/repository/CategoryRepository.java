package com.team28.borrow.domain.repository;

import com.team28.borrow.domain.model.Category;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface CategoryRepository {
    Flowable<List<Category>> streamCategory();

    Completable addCategory(Category category);
}
