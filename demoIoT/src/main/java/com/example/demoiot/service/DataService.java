package com.example.demoiot.service;

import com.example.demoiot.dto.DataDto;
import com.example.demoiot.dto.WeatherDto;
import com.example.demoiot.model.Data;
import com.example.demoiot.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DataService {

    default void saveData(Data data) {}

    default List<DataDto> getTenLastestData() {
        return null;
    }

    default WeatherDto getWeather(){return null;}

    default DataDto getOneData(){return null;}
}
