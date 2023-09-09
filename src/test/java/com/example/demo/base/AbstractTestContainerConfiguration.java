package com.example.demo.base;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestContainerConfiguration {

    static MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.33");

    @BeforeAll
    static void beforeAll() {
        MYSQL_CONTAINER.withReuse(true);
        MYSQL_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("book.db.username", MYSQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("book.db.password", MYSQL_CONTAINER::getPassword);
        dynamicPropertyRegistry.add("book.db.url", MYSQL_CONTAINER::getJdbcUrl);
    }

}
