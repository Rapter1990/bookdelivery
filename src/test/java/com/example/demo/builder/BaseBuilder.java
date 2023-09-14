package com.example.demo.builder;

import lombok.SneakyThrows;

/**
 * Abstract base class for building objects of type T.
 *
 * @param <T> the type of object to be built
 */
public abstract class BaseBuilder<T> {

    /**
     * Constructs a new instance of the specified class and initializes it for building.
     *
     * @param clazz the class of type T
     */
    @SneakyThrows
    public BaseBuilder(Class<T> clazz) {
        this.data = clazz.getDeclaredConstructor().newInstance();
    }

    T data;


    /**
     * Builds and returns the constructed object of type T.
     *
     * @return the constructed object
     */
    public T build() {
        return data;
    }
}
