package com.example.demo.model;

import com.example.demo.model.enums.Role;
import jakarta.persistence.*;


@MappedSuperclass
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;
}
