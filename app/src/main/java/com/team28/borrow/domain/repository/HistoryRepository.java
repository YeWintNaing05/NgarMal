package com.team28.borrow.domain.repository;

import android.support.annotation.NonNull;

import com.team28.borrow.domain.model.PostItem;

import java.util.List;

import io.reactivex.Flowable;

public interface HistoryRepository {
    Flowable<List<PostItem>> getItemsByCurrentUser(@NonNull String uuid);
}
