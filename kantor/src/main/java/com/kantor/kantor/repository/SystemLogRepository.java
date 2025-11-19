package com.kantor.kantor.repository;

import com.kantor.kantor.model.SystemLog;
import com.kantor.kantor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    List<SystemLog> findByUser(User user);
}