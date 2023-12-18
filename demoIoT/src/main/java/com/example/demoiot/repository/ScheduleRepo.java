package com.example.demoiot.repository;

import com.example.demoiot.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
}
