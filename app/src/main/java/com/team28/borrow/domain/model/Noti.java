package com.team28.borrow.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Noti {


    public abstract String noti_id();

    public abstract String user_id();

    public abstract String item_id();

    public abstract String date();

    public abstract String content();

    public abstract String title();


    public static Noti create(String user_id, String noti_id, String item_id, String date, String content, String title) {
        return builder().user_id(user_id).noti_id(noti_id).item_id(item_id).date(date).content(content).title(title).build();
    }

    public static Noti progress() {
        return builder().user_id("").noti_id("").item_id("").date("").content("").title("").build();

    }

    public static Noti.Builder builder() {
        return new AutoValue_Noti.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {


        public abstract Builder noti_id(String noti_id);

        public abstract Builder user_id(String user_id);

        public abstract Builder title(String state);

        public abstract Builder item_id(String item_id);


        public abstract Builder content(String content);

        public abstract Builder date(String date);


        public abstract Noti build();


    }
}
