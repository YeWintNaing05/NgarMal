package com.team28.borrow.presentation.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CategoryModel {

    public static CategoryModel create(String name, int img) {
        return builder()
                .name(name)
                .img(img)
                .build();
    }

    public static CategoryModel.Builder builder() {
        return new AutoValue_CategoryModel.Builder();
    }


    public abstract String name();

    public abstract int img();



    @AutoValue.Builder
    public abstract static class Builder {


        public abstract Builder name(String name);

        public abstract Builder img(int img);



        public abstract CategoryModel build();
    }
}
