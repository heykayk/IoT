package com.example.demoiot.dto;

import com.example.demoiot.constant.SystemConstant;
import com.example.demoiot.model.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDto {
    private long id;
    private int humidityPercentage;
    private String time;
    private int status;

    public DataDto(Data data){
        if(!ObjectUtils.isEmpty(data)){
            this.id = data.getId();
            this.humidityPercentage = data.getHumidityPercentage();
            this.time = SystemConstant.sdfNormal.format(new Date(data.getTimeNow()));
            this.status = data.getStatus();
        }
    }
}
