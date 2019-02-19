package com.team28.borrow.presentation.feature.history;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.team28.borrow.domain.usecase.GetBorrowItems;
import com.team28.borrow.domain.usecase.GetHistoryItem;
import com.team28.borrow.presentation.base.BaseViewModel;
import com.team28.borrow.presentation.mapper.BorrowFormMapper;
import com.team28.borrow.presentation.mapper.ItemMapper;
import com.team28.borrow.presentation.model.BorrowFormModel;
import com.team28.borrow.presentation.model.ItemModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class HistoryViewModel extends BaseViewModel {

    private GetHistoryItem getHistoryItem;
    private GetBorrowItems getBorrowItems;
    private final ItemMapper mapper;
    private final BorrowFormMapper borrowFormMapper;
    private BehaviorSubject<List<ItemModel>> itemHistorySubject = BehaviorSubject.create();
    private BehaviorSubject<List<BorrowFormModel>> itemBorrowHistorySubject = BehaviorSubject.create();


    @Inject
    HistoryViewModel(GetHistoryItem getHistoryItem, GetBorrowItems getBorrowItems, ItemMapper mapper, BorrowFormMapper borrowFormMapper) {
        this.getHistoryItem = getHistoryItem;
        this.getBorrowItems = getBorrowItems;
        this.mapper = mapper;
        this.borrowFormMapper = borrowFormMapper;
    }

    void getHistoryItems() {
        add(getHistoryItem.execute(new GetHistoryItem.Action(FirebaseAuth.getInstance().getUid()))
                .map(mapper::map)
                .subscribe(itemHistorySubject::onNext, this::showError)
        );
    }

    void getHistoryBorrowItems() {
        add(getBorrowItems.execute(new GetBorrowItems.Action(FirebaseAuth.getInstance().getUid()))
                .map(borrowFormMapper::map)
                .subscribe(itemBorrowHistorySubject::onNext, this::showError)
        );
    }

    private void showError(Throwable throwable) {
        Log.e("Error Data", throwable.getMessage());
    }

    Observable<List<ItemModel>> getItemListStream() {
        return itemHistorySubject;

    }

    Observable<List<BorrowFormModel>> getBorrowDataListStream() {
        return itemBorrowHistorySubject;

    }
}
