package com.example.demo.logging.service;

import com.example.demo.logging.entity.LogEntity;

/**
 * Service interface for saving {@link LogEntity} to the database.
 */
public interface LogService {
    void saveLogToDatabase(LogEntity logEntity);
}
