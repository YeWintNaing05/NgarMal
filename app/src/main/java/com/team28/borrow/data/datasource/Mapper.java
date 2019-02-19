package com.team28.borrow.data.datasource;

//D refers to domain model,and E refers to entity
public abstract class Mapper<D, E> {

    public abstract D map(E entity);
}