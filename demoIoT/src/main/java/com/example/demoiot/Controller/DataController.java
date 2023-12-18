package com.example.demoiot.Controller;

import com.example.demoiot.dto.DataDto;
import com.example.demoiot.dto.WeatherDto;
import com.example.demoiot.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")

public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping()
    public String displayIndex(){
        return "index";
    }

    @GetMapping("/get-ten-result")
    @ResponseBody
    public List<DataDto> getSevenResult(){
        return this.dataService.getTenLastestData();
    }

    @GetMapping("/weather")
    @ResponseBody
    public WeatherDto getWeather(){
        return this.dataService.getWeather();
    }
}
