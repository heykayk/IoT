package com.example.demoiot.service.serviceIml;

import com.example.demoiot.dto.HistoryDto;
import com.example.demoiot.model.History;
import com.example.demoiot.repository.HistoryRepo;
import com.example.demoiot.service.HistoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceIml implements HistoryService {
    @Autowired
    private HistoryRepo historyRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(History h) {
        this.historyRepo.save(h);
    }

    @Override
    public List<HistoryDto> getListHistory() {
        Query query = this.entityManager.createQuery("select new com.example.demoiot.dto.HistoryDto(h) from History h");

        return query.getResultList();
    }
}
