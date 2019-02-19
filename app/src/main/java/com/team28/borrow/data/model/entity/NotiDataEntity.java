package com.team28.borrow.data.model.entity;

import com.google.auto.value.AutoValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import io.reactivex.annotations.NonNull;
import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

@AutoValue
@FirebaseValue
public abstract class NotiDataEntity extends Model {

    public abstract String item_id();

    public abstract String user_id();

    public abstract String date();

    public abstract String content();

    public abstract String title();


    public static NotiDataEntity create(String user_id, String item_id, String date, String content, String title) {
        return builder().user_id(user_id).item_id(item_id).date(date).content(content).title(title).build();
    }


    public static NotiDataEntity.Builder builder() {
        return new AutoValue_NotiDataEntity.Builder();
    }

    public Object toFirebaseValue() {
        return new AutoValue_NotiDataEntity.FirebaseValue(this);
    }

    @NonNull
    public static NotiDataEntity create(QueryDocumentSnapshot dataSnapshot) {
        return dataSnapshot.toObject(AutoValue_NotiDataEntity.FirebaseValue.class).toAutoValue();
    }


    @AutoValue.Builder
    public abstract static class Builder {


        public abstract Builder title(String title);

        public abstract Builder user_id(String user_id);

        public abstract Builder item_id(String item_id);


        public abstract Builder content(String content);

        public abstract Builder date(String date);


        public abstract NotiDataEntity build();


    }
}
