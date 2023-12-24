package com.example.demoiot.service;

import com.example.demoiot.dto.ScheduleDto;
import com.example.demoiot.model.Schedule;

import java.util.List;

public interface ScheduleService {
    default void save(Schedule s){}

    default void deleteById(int id){}

    default List<ScheduleDto> getAllSchedule(){return  null;}

    default void setTime(ScheduleDto scheduleDto){}
}


