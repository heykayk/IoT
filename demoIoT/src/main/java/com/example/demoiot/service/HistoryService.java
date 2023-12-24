package com.example.demoiot.service;

import com.example.demoiot.dto.HistoryDto;
import com.example.demoiot.model.History;

import java.util.List;

public interface HistoryService {
    default void save(History h){}

    default List<HistoryDto> getListHistory(){return null;}
}
