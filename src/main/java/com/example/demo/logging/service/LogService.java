package com.example.demo.logging.service;

import com.example.demo.logging.entity.LogEntity;

public interface LogService {
    void saveLogToDatabase(LogEntity logEntity);
}
