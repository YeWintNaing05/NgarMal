package com.team28.borrow.presentation.mapper;

import android.content.Context;

import com.team28.borrow.domain.model.PostItem;
import com.team28.borrow.presentation.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ItemMapper extends ViewStateMapper<ItemModel, PostItem> {

    @Inject
    public ItemMapper(Context context) {
        super(context);
    }

    @Override
    public ItemModel map(PostItem domainModel) {
        return ItemModel.create(domainModel.item_id(), domainModel.state(), domainModel.item_name(), domainModel.price_per_day(), domainModel.price_per_week(), domainModel.price_per_month(), domainModel.user_id(), domainModel.brand_name(), domainModel.engine_power(), domainModel.seat_num(),
                domainModel.gas_or_oil(), domainModel.zoom(), domainModel.focus_condition(), domainModel.img(),
                domainModel.category(), domainModel.date(), domainModel.damage(), domainModel.phone(), domainModel.name());
    }

    public List<ItemModel> map(List<PostItem> domainModel) {
        List<ItemModel> items
                = null;
        if (domainModel != null) {
            items = new ArrayList<>();
            for (PostItem domainData : domainModel) {
                items.add(map(domainData));
            }
        }
        return items;

    }


    public PostItem reverse(ItemModel itemModel) {
        return PostItem.create(itemModel.item_id(), itemModel.state(), itemModel.item_name(), itemModel.price_per_day(), itemModel.price_per_week(), itemModel.price_per_month(), itemModel.user_id(), itemModel.brand_name(), itemModel.engine_power(), itemModel.seat_num(),
                itemModel.gas_or_oil(), itemModel.zoom(), itemModel.focus_condition(),
                itemModel.img(), itemModel.category(), itemModel.date(), itemModel.damage(), itemModel.phone(), itemModel.name());


    }

}
