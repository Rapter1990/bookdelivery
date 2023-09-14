package com.example.demo.base.pool;

import java.util.HashSet;
import java.util.Set;

public abstract class ObjectPool<T> {

    private Set<T> available = new HashSet<>();
    private Set<T> inUse = new HashSet<>();

    protected abstract T create();

    public T checkOut() {
        if (available.isEmpty()) {
            available.add(create());
        }
        T object = available.iterator().next();
        available.remove(object);
        inUse.add(object);

        return object;
    }

    public void checkIn(T object) {
        available.add(object);
        inUse.remove(object);

        inUse.clear();
        available.clear();
    }

}
