package com.kantor.kantor.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kantor.kantor.model.SystemLog;
import com.kantor.kantor.model.User;
import com.kantor.kantor.repository.SystemLogRepository;

@Service
public class SystemLogService {

    private final SystemLogRepository systemLogRepository;

    @Autowired
    public SystemLogService(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    public SystemLog logAction(String action, String description, User user) {
        SystemLog log = new SystemLog();
        log.setAction(action);
        log.setDescription(description);
        log.setUser(user);
        log.setTimestamp(LocalDateTime.now());
        
        return systemLogRepository.save(log);
    }
    
    public List<SystemLog> getUserLogs(User user) {
        return systemLogRepository.findByUser(user);
    }
    
    public List<SystemLog> getAllLogs() {
        return systemLogRepository.findAll();
    }
}