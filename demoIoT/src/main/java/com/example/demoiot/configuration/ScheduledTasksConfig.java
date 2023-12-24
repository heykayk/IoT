package com.example.demoiot.configuration;

import com.example.demoiot.dto.ScheduleDto;
import com.example.demoiot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledTasksConfig {
    @Autowired
    private ScheduleService scheduleService;

    private static long secondsBefor = new Date().getTime() / 1000;
    private static long secondsNow = new Date().getTime() / 1000 + 11;

    private List<ScheduleDto> list = new ArrayList<>();

    @Scheduled(fixedDelay = 1000)
    public void checkForConfigUpdates() {
        list = this.scheduleService.getAllSchedule();
        checkTime();
    }


    private void checkTime(){
        if(secondsNow - secondsBefor > 10){
            LocalTime currentTime = LocalTime.now();
            for(ScheduleDto i:this.list){
                LocalTime targetTime = LocalTime.parse(i.getThoigian());
                Duration duration = Duration.between(currentTime, targetTime);
                if(Math.abs(duration.getSeconds()) <= 1){
                    secondsBefor = secondsNow;
                    this.scheduleService.setTime(i);
                    return;
                }
            }
        }
        if(new Date().getTime() / 1000 > secondsNow){
            secondsNow = new Date().getTime()/1000;
        }
    }

}

