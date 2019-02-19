package com.team28.borrow.data.model.entity;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import io.reactivex.annotations.NonNull;

@IgnoreExtraProperties
public class Model {
    @Exclude
    public String id;

    public <T extends Model> T withId(@NonNull final String id) {
        this.id = id;
        return (T) this;
    }
}