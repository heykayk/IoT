package com.example.demoiot.service.serviceIml;

import com.example.demoiot.dto.ScheduleDto;
import com.example.demoiot.model.Schedule;
import com.example.demoiot.repository.ScheduleRepo;
import com.example.demoiot.service.ScheduleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScheduleServiceIml implements ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(Schedule s) {
        this.scheduleRepo.save(s);
    }

    @Override
    public void deleteById(int id) {
        this.scheduleRepo.deleteById(id);
    }

    @Override
    public List<ScheduleDto> getAllSchedule() {
        Query query = this.entityManager.createQuery("select new com.example.demoiot.dto.ScheduleDto(s) from Schedule s");

        return query.getResultList();
    }
}
