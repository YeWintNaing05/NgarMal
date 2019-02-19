package com.team28.borrow.presentation.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BorrowFormModel implements Parcelable {


    public static BorrowFormModel create(String name, String item_name, String item_brand, String img, String state, String item_id, String post_user_id, String user_id, String address, String phone, String start_borrow_date, String number_of_borrow_day, String fee, String date) {
        return builder()
                .img(img)
                .item_name(item_name)
                .item_brand(item_brand)
                .date(date)
                .post_user_id(post_user_id)
                .item_id(item_id)
                .current_user_id(user_id)
                .state(state)
                .customer_name(name)
                .address(address)
                .phone(phone)
                .start_borrow_date(start_borrow_date)
                .number_of_borrow_day(number_of_borrow_day)
                .fee(fee)
                .build();
    }


    public static BorrowFormModel.Builder builder() {
        return new AutoValue_BorrowFormModel.Builder();
    }


    public abstract String state();

    public abstract String img();

    public abstract String item_id();

    public abstract String item_name();

    public abstract String item_brand();

    public abstract String customer_name();

    public abstract String date();

    public abstract String address();

    public abstract String phone();

    public abstract String start_borrow_date();

    public abstract String number_of_borrow_day();

    public abstract String fee();

    public abstract String current_user_id();

    public abstract String post_user_id();


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder customer_name(String name);

        public abstract Builder item_id(String name);

        public abstract Builder item_name(String name);

        public abstract Builder item_brand(String name);

        public abstract Builder img(String img);

        public abstract Builder address(String address);

        public abstract Builder phone(String phone);

        public abstract Builder start_borrow_date(String start_borrow_date);

        public abstract Builder number_of_borrow_day(String number_of_borrow_day);

        public abstract Builder fee(String fee);

        public abstract Builder state(String state);

        public abstract Builder date(String date);

        public abstract Builder current_user_id(String user_id);

        public abstract Builder post_user_id(String user_id);

        public abstract BorrowFormModel build();


    }

}