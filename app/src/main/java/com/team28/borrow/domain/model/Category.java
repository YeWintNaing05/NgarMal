package com.team28.borrow.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Category extends DomainModel {

    public static Builder success() {
        return builder()
                .error(null)
                .state(State.SUCCESS);
    }

    public static Category progress() {
        return builder()
                .id(1)
                .name("")
                .type("")
                .category_img(0)
                .error(null)
                .state(State.PROGRESS)
                .build();
    }

    public static Category error(Throwable error) {
        return builder()
                .id(1)
                .name("")
                .type("")
                .category_img(0)
                .error(error)
                .state(State.ERROR)
                .build();
    }

    public abstract int id();

    public abstract String type();


    public abstract String name();
    public abstract int category_img();



    public static Category create(int id, String name, String type,
                                  Throwable error, State state) {
        return builder()
                .id(id)
                .name(name)
                .type(type)
                .error(error)
                .state(state)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_Category.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String category_name);

        public abstract Builder type(String category_type);
        public abstract Builder category_img(int img_location);


        public abstract Builder error(Throwable error);

        public abstract Builder state(State state);

        public abstract Category build();
    }
}