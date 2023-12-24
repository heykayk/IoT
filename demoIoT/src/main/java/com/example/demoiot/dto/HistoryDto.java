package com.example.demoiot.dto;

import com.example.demoiot.constant.SystemConstant;
import com.example.demoiot.model.History;
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
public class HistoryDto {
    private int id;
    private String thoigian;
    private int thoiluong;

    public HistoryDto(History history){
        if(!ObjectUtils.isEmpty(history)){
            this.id = id;
            this.thoigian = SystemConstant.sdfNormal.format(new Date(history.getThoigian()));
            this.thoiluong = history.getThoiluong();
        }
    }
}
