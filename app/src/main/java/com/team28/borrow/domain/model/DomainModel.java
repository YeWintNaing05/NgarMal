package com.team28.borrow.domain.model;

import io.reactivex.annotations.Nullable;

public abstract class DomainModel {
    @Nullable
    public abstract Throwable error();

    public abstract State state();
}
