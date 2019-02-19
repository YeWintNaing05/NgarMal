package com.team28.borrow.presentation.mapper;

import android.content.Context;

import com.team28.borrow.domain.model.BorrowForm;
import com.team28.borrow.presentation.model.BorrowFormModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BorrowFormMapper extends ViewStateMapper<BorrowFormModel, BorrowForm> {

    @Inject
    BorrowFormMapper(Context context) {
        super(context);
    }

    @Override
    public BorrowFormModel map(BorrowForm domainModel) {
        return BorrowFormModel.create(domainModel.customer_name(), domainModel.item_name(), domainModel.item_brand(), domainModel.img(), domainModel.state(), domainModel.item_id(), domainModel.post_user_id(), domainModel.current_user_id(), domainModel.address(), domainModel.phone(),
                domainModel.start_borrow_date(), domainModel.number_of_borrow_day(), domainModel.fee(), domainModel.date());
    }

    public List<BorrowFormModel> map(List<BorrowForm> borrowFormList) {
        List<BorrowFormModel> items = null;
        if (borrowFormList != null) {
            items = new ArrayList<>();
            for (BorrowForm domainData : borrowFormList) {
                items.add(map(domainData));
            }
        }
        return items;


    }

    public BorrowForm reverse(BorrowFormModel borrowFormModel) {
        return BorrowForm.create(borrowFormModel.customer_name(), borrowFormModel.item_name(), borrowFormModel.item_brand(), borrowFormModel.img(), borrowFormModel.state(), borrowFormModel.item_id(), borrowFormModel.post_user_id(), borrowFormModel.current_user_id(), borrowFormModel.address(), borrowFormModel.phone(),
                borrowFormModel.start_borrow_date(), borrowFormModel.number_of_borrow_day(), borrowFormModel.fee(), borrowFormModel.date());

    }


}