package com.team28.borrow.data.datasource.noti.mapper;


import com.team28.borrow.data.datasource.Mapper;
import com.team28.borrow.data.model.entity.NotiDataEntity;
import com.team28.borrow.domain.model.Noti;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NotiDataEntityMapper extends Mapper<Noti, NotiDataEntity> {

    @Inject
    public NotiDataEntityMapper() {

    }


    @Override
    public Noti map(NotiDataEntity entity) {
        return Noti.create(entity.user_id(), entity.id, entity.item_id(), entity.date(), entity.content(), entity.title());
    }

    public List<Noti> map(List<NotiDataEntity> entities) {
        List<Noti> items = null;
        if (entities != null) {
            items = new ArrayList<>();
            for (NotiDataEntity entity : entities) {
                items.add(map(entity));
            }
        }
        return items;
    }

    public NotiDataEntity reverse(Noti noti) {
        return NotiDataEntity.create(noti.user_id(), noti.item_id(), noti.date(), noti.content(), noti.title()).withId(noti.noti_id());


    }
}
