package com.team28.borrow.domain.model;


import com.google.auto.value.AutoValue;

import io.reactivex.annotations.Nullable;


@AutoValue
public abstract class PostItem {


    public static PostItem create(String id, String state, String name, Integer per_per_day, Integer per_per_week, Integer per_per_month, String user_id, String brand_name, String engine_power, String seat_num, String gas_or_oil
            , String zoom, String focus, String img, String category, String date, String damage, String phone, String user_name) {
        return builder()
                .item_id(id)
                .price_per_day(per_per_day)
                .price_per_week(per_per_week)
                .price_per_month(per_per_month)
                .user_id(user_id)
                .state(state)
                .item_name(name)
                .brand_name(brand_name)
                .engine_power(engine_power)
                .seat_num(seat_num)
                .gas_or_oil(gas_or_oil)
                .zoom(zoom)
                .focus_condition(focus)
                .img(img)
                .category(category)
                .date(date)
                .damage(damage)
                .phone(phone)
                .name(user_name)
                .build();
    }


    public static PostItem.Builder builder() {
        return new AutoValue_PostItem.Builder();
    }


    public abstract String item_name();

    public abstract String item_id();

    public abstract String damage();

    public abstract String state();

    public abstract String user_id();

    public abstract String name();

    @javax.annotation.Nullable
    public abstract String brand_name();

    public abstract String phone();

    //for car
    @javax.annotation.Nullable
    public abstract String engine_power();

    @javax.annotation.Nullable
    public abstract String gas_or_oil();

    @javax.annotation.Nullable
    public abstract String seat_num();

    //for camera
    @javax.annotation.Nullable
    public abstract String zoom();

    @javax.annotation.Nullable
    public abstract String focus_condition();


    public abstract String img();

    public abstract String date();

    public abstract String category();

    @Nullable
    public abstract Integer price_per_day();

    @Nullable
    public abstract Integer price_per_week();

    @Nullable
    public abstract Integer price_per_month();


    @AutoValue.Builder
    public abstract static class Builder {


        public abstract Builder item_name(String name);

        public abstract Builder item_id(String name);

        public abstract Builder state(String state);

        public abstract Builder phone(String phone);

        public abstract Builder user_id(String user_id);

        public abstract Builder name(String name);

        public abstract Builder brand_name(String brand_name);

        public abstract Builder engine_power(String engine_power);

        public abstract Builder seat_num(String seat_num);

        public abstract Builder gas_or_oil(String gas_or_oil);

        public abstract Builder zoom(String zoom);

        public abstract Builder focus_condition(String focus_condition);

        public abstract Builder img(String img);

        public abstract Builder date(String date);

        public abstract Builder category(String category);

        public abstract Builder damage(String damage);

        public abstract Builder price_per_day(Integer price);

        public abstract Builder price_per_week(Integer price);

        public abstract Builder price_per_month(Integer price);


        public abstract PostItem build();


    }


}
