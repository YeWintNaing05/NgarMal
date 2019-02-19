package com.team28.borrow.data.model.entity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import javax.annotation.Nullable;

import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;


@AutoValue
@FirebaseValue
public abstract class ItemDataEntity extends Model implements Parcelable {


    public static ItemDataEntity create(String name, String state, Integer per_per_day, Integer per_per_week, Integer per_per_month, String user_id, String brand_name, String engine_power, String seat_num, String gas_or_oil, String zoom, String focus, String img
            , String category, String date, String damage, String phone, String user_name) {
        return builder()
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


    public static ItemDataEntity.Builder builder() {
        return new AutoValue_ItemDataEntity.Builder();
    }

    public Object toFirebaseValue() {
        return new AutoValue_ItemDataEntity.FirebaseValue(this);
    }

    @NonNull
    public static ItemDataEntity create(QueryDocumentSnapshot dataSnapshot) {
        return dataSnapshot.toObject(AutoValue_ItemDataEntity.FirebaseValue.class).toAutoValue();
    }

    public abstract String item_name();

    public abstract String name();


    public abstract String state();

    public abstract String phone();

    public abstract String user_id();

    @Nullable
    public abstract String brand_name();

    //for car
    @Nullable
    public abstract String engine_power();

    @Nullable
    public abstract String gas_or_oil();

    @Nullable
    public abstract String seat_num();

    //for camera
    @Nullable
    public abstract String zoom();

    @Nullable
    public abstract String focus_condition();


    public abstract String img();

    public abstract String date();

    public abstract String damage();

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


        public abstract Builder state(String state);

        public abstract Builder name(String name);

        public abstract Builder phone(String phone);

        public abstract Builder user_id(String user_id);

        public abstract Builder brand_name(String brand_name);

        public abstract Builder engine_power(String engine_power);

        public abstract Builder seat_num(String seat_num);

        public abstract Builder gas_or_oil(String gas_or_oil);

        public abstract Builder zoom(String zoom);

        public abstract Builder focus_condition(String focus_condition);

        public abstract Builder img(String img);

        public abstract Builder date(String date);

        public abstract Builder damage(String damage);

        public abstract Builder category(String category);

        public abstract Builder price_per_day(Integer price);

        public abstract Builder price_per_week(Integer price);

        public abstract Builder price_per_month(Integer price);


        public abstract ItemDataEntity build();


    }


}
