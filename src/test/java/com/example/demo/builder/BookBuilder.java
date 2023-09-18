package com.example.demo.builder;

import com.example.demo.model.Book;
import com.example.demo.util.RandomUtil;

import java.math.BigDecimal;

public class BookBuilder extends BaseBuilder<Book> {
    public BookBuilder() {
        super(Book.class);
    }

    public BookBuilder withValidFields() {
        return this
                .withId(RandomUtil.generateUUID())
                .withIsbn(RandomUtil.generateRandomString())
                .withName(RandomUtil.generateRandomString())
                .withAuthorFullName(RandomUtil.generateRandomString())
                .withStock(RandomUtil.generateRandomInteger(1, 100))
                .withPrice(RandomUtil.generateRandomBigDecimal(1.0, 200.0));
    }

    public BookBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public BookBuilder withIsbn(String isbn) {
        data.setIsbn(isbn);
        return this;
    }

    public BookBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public BookBuilder withAuthorFullName(String authorFullName) {
        data.setAuthorFullName(authorFullName);
        return this;
    }

    public BookBuilder withStock(Integer stock) {
        data.setStock(stock);
        return this;
    }

    public BookBuilder withPrice(BigDecimal price) {
        data.setPrice(price);
        return this;
    }
}
