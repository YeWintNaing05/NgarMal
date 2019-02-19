package com.team28.borrow.data.datasource.borrow_form_data.mapper;

import com.team28.borrow.data.datasource.Mapper;
import com.team28.borrow.data.model.entity.BorrowFormDataEntity;
import com.team28.borrow.domain.model.BorrowForm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BorrowFormDataEntityMapper extends Mapper<BorrowForm, BorrowFormDataEntity> {


    @Inject
    BorrowFormDataEntityMapper() {

    }

    @Override
    public BorrowForm map(BorrowFormDataEntity entity) {
        return BorrowForm.create(entity.customer_name(), entity.item_name(), entity.item_brand(), entity.img(), entity.state(), entity.item_id(), entity.post_user_id(), entity.current_user_id(), entity.address(), entity.phone(),
                entity.start_borrow_date(), entity.number_of_borrow_day(), entity.fee(), entity.date());

    }

    public List<BorrowForm> map(List<BorrowFormDataEntity> borrowFormDataEntities) {
        List<BorrowForm> items = null;
        if (borrowFormDataEntities != null) {
            items = new ArrayList<>();
            for (BorrowFormDataEntity domainData : borrowFormDataEntities) {
                items.add(map(domainData));
            }
        }
        return items;


    }

    public BorrowFormDataEntity reverse(BorrowForm borrowForm) {
        return BorrowFormDataEntity.create(borrowForm.customer_name(), borrowForm.item_name(), borrowForm.item_brand(), borrowForm.img(), borrowForm.state(), borrowForm.item_id(), borrowForm.post_user_id(), borrowForm.current_user_id(), borrowForm.address(), borrowForm.phone(),
                borrowForm.start_borrow_date(), borrowForm.number_of_borrow_day(), borrowForm.fee(), borrowForm.date());

    }

}
