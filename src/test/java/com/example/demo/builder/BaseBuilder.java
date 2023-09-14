package com.example.demo.builder;

import lombok.SneakyThrows;

public abstract class BaseBuilder<T> {

    @SneakyThrows
    public BaseBuilder(Class<T> clazz){
        this.clazz = clazz;
        this.data = clazz.getDeclaredConstructor().newInstance();
    }

    T data;
    Class<T> clazz;

    public T build() {
        return data;
    }
}
