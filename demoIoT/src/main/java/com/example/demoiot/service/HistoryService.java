package com.example.demoiot.service;

import com.example.demoiot.model.History;

public interface HistoryService {
    default void save(History h){}

}
