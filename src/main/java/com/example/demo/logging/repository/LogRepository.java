package com.example.demo.logging.repository;

import com.example.demo.logging.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the {@link LogEntity} class.
 */
public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
