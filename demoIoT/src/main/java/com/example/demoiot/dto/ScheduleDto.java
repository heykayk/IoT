package com.example.demoiot.dto;

import com.example.demoiot.model.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDto {
    private int id;

    private String thoigian;

    private int thoiluong;

    public ScheduleDto(Schedule s){
        if(!ObjectUtils.isEmpty(s)){
            this.id = s.getId();
            this.thoigian = LocalTime.ofSecondOfDay(s.getThoigian()).toString();
            this.thoiluong = s.getThoiluong() / 60;
        }
    }
}
