package com.example.demoiot.service.serviceIml;

import com.example.demoiot.dto.DataDto;
import com.example.demoiot.dto.ScheduleDto;
import com.example.demoiot.dto.WeatherDto;
import com.example.demoiot.gateway.MqttGateway;
import com.example.demoiot.model.History;
import com.example.demoiot.model.Schedule;
import com.example.demoiot.repository.ScheduleRepo;
import com.example.demoiot.service.DataService;
import com.example.demoiot.service.HistoryService;
import com.example.demoiot.service.ScheduleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ScheduleServiceIml implements ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MqttGateway mqttGateway;

    @Autowired
    private DataService dataService;

    @Autowired
    private HistoryService historyService;

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

    @Override
    public void setTime(ScheduleDto scheduleDto) {
        WeatherDto weatherDto = this.dataService.getWeather();
        DataDto dataDto = this.dataService.getOneData();
        if(weatherDto.getHumidity() <= 80 && weatherDto.getTemp() < 30 && weatherDto.getTemp() > 10 && dataDto.getHumidityPercentage() <= 100){
            mqttGateway.sendToMqtt(String.valueOf(scheduleDto.getThoiluong() * 60), "setTime");
            History history = new History();
            history.setThoigian(new Date().getTime());
            history.setThoiluong(scheduleDto.getThoiluong());
            this.historyService.save(history);
        }
        System.out.println("hello world");
    }
}
