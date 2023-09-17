package com.example.demo.logging.service.impl;

import com.example.demo.logging.entity.LogEntity;
import com.example.demo.logging.repository.LogRepository;
import com.example.demo.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of the {@link LogService} interface for saving log entries to the database.
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    /**
     * Save a {@link LogEntity} entry to the database.
     *
     * @param logEntity The {@link LogEntity} entity to be saved.
     */
    @Override
    public void saveLogToDatabase(LogEntity logEntity) {
        logEntity.setTime(LocalDateTime.now());
        logRepository.save(logEntity);
    }
}
