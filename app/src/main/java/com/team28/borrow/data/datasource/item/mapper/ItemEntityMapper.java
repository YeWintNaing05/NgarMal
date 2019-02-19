package com.team28.borrow.data.datasource.item.mapper;

import com.team28.borrow.data.datasource.Mapper;
import com.team28.borrow.data.model.entity.ItemDataEntity;
import com.team28.borrow.domain.model.PostItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ItemEntityMapper extends Mapper<PostItem, ItemDataEntity> {


    @Inject
    ItemEntityMapper() {

    }

    @Override
    public PostItem map(ItemDataEntity entity) {
        return PostItem.create(entity.id, entity.state(), entity.item_name(), entity.price_per_day(), entity.price_per_week(), entity.price_per_month(), entity.user_id(), entity.brand_name(), entity.engine_power(), entity.seat_num(),
                entity.gas_or_oil(), entity.zoom(), entity.focus_condition(), entity.img(), entity.category(),
                entity.date(), entity.damage(), entity.phone(),entity.name());

    }

    public List<PostItem> map(List<ItemDataEntity> entities) {
        List<PostItem> items = null;
        if (entities != null) {
            items = new ArrayList<>();
            for (ItemDataEntity entity : entities) {
                items.add(map(entity));
            }
        }
        return items;
    }


    public ItemDataEntity reverse(PostItem postItem) {
        return ItemDataEntity.create(postItem.item_name(), postItem.state(),
                postItem.price_per_day(), postItem.price_per_week(), postItem.price_per_month(), postItem.user_id(),
                postItem.brand_name(), postItem.engine_power(), postItem.seat_num(),
                postItem.gas_or_oil(), postItem.zoom(), postItem.focus_condition()
                , postItem.img(), postItem.category(), postItem.date(), postItem.damage(), postItem.phone(),postItem.name()
        ).withId(postItem.item_id());


    }
}
