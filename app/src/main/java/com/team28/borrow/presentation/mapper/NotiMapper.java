package com.team28.borrow.presentation.mapper;

import android.content.Context;


import com.team28.borrow.domain.model.Noti;
import com.team28.borrow.presentation.model.NotiModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NotiMapper extends ViewStateMapper<NotiModel, Noti> {

    @Inject
    public NotiMapper(Context context) {
        super(context);
    }


    public List<NotiModel> map(List<Noti> entities) {
        List<NotiModel> items = null;
        if (entities != null) {
            items = new ArrayList<>();
            for (Noti entity : entities) {
                items.add(map(entity));
            }
        }
        return items;
    }

    public Noti reverse(NotiModel noti) {
        return Noti.create(noti.user_id(), noti.noti_id(), noti.item_id(), noti.date(), noti.content(), noti.title());


    }

    @Override
    public NotiModel map(Noti domainModel) {
        return NotiModel.create(domainModel.user_id(), domainModel.noti_id(), domainModel.item_id(), domainModel.date(), domainModel.content(), domainModel.title());

    }
}
