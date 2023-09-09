package com.example.demo.model;

import com.example.demo.model.enums.TokenClaims;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "CREATED_USER")
    protected String createdUser;

    @Column(name = "CREATED_AT")
    protected LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdUser = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !"anonymousUser".equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USERNAME.getValue()).toString())
                .orElse("anonymousUser");
        this.createdAt = LocalDateTime.now();
    }


    @Column(name = "UPDATED_USER")
    protected String updatedUser;

    @Column(name = "UPDATED_AT")
    protected LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedUser = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !"anonymousUser".equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.USERNAME.getValue()).toString())
                .orElse("anonymousUser");
        this.updatedAt = LocalDateTime.now();
    }
}
