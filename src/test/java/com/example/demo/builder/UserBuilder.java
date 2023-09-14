package com.example.demo.builder;

import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.util.RandomUtil;

public class UserBuilder extends BaseBuilder<User> {

    public UserBuilder() {
        super(User.class);
    }

    public UserBuilder customer() {
        return this
                .withId(1L)
                .withFullName(RandomUtil.generateRandomString())
                .withUsername(RandomUtil.generateRandomString())
                .withEmail(RandomUtil.generateRandomString().concat("@bookdelivery.com"))
                .withRole(Role.ROLE_CUSTOMER);
    }

    public UserBuilder admin() {
        return this.customer()
                .withRole(Role.ROLE_ADMIN);
    }

    public UserBuilder withId(Long id) {
        data.setId(id);
        return this;
    }

    public UserBuilder withFullName(String fullName) {
        data.setFullName(fullName);
        return this;
    }

    public UserBuilder withUsername(String username) {
        data.setUsername(username);
        return this;
    }

    public UserBuilder withEmail(String email) {
        data.setEmail(email);
        return this;
    }

    public UserBuilder withRole(Role role) {
        data.setRole(role);
        return this;
    }

}
