package com.team28.borrow.presentation.feature.borrow;

import android.util.Log;

import com.team28.borrow.domain.usecase.ApplyBorrowItem;
import com.team28.borrow.presentation.base.BaseViewModel;
import com.team28.borrow.presentation.mapper.BorrowFormMapper;
import com.team28.borrow.presentation.model.BorrowFormModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class BorrowFormViewModel extends BaseViewModel {

    private static final String TAG = "borrow_data";
    private ApplyBorrowItem applyBorrowItem;
    private final BorrowFormMapper borrowFormMapper;

    private BehaviorSubject<String> itemsModelSubject = BehaviorSubject.create();

    @Inject
    BorrowFormViewModel(ApplyBorrowItem applyBorrowItem, BorrowFormMapper borrowFormMapper) {
        this.applyBorrowItem = applyBorrowItem;
        this.borrowFormMapper = borrowFormMapper;
    }


    void applyBorrowForm(BorrowFormModel model) {
        ApplyBorrowItem.Action action = new ApplyBorrowItem.Action(borrowFormMapper.reverse(model));
        add(applyBorrowItem.execute(action).subscribe(() -> itemsModelSubject.onNext("Borrow Form Successfully applied"), this::showError));
    }


    private void showError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }


    Observable<String> getItemStream() {
        return itemsModelSubject;
    }


}
