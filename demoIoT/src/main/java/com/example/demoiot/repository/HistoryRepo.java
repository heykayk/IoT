package com.example.demoiot.repository;

import com.example.demoiot.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo extends JpaRepository<History, Integer> {
}
