package com.example.demoiot.Controller;

import com.example.demoiot.dto.HistoryDto;
import com.example.demoiot.dto.ScheduleDto;
import com.example.demoiot.model.Schedule;
import com.example.demoiot.service.HistoryService;
import com.example.demoiot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/time")
public class TimeController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HistoryService historyService;

    @GetMapping()
    public String displayAll(){
        return "all";
    }

    @GetMapping("/all-schedule")
    @ResponseBody
    public List<ScheduleDto> getListSchedule(){
        return this.scheduleService.getAllSchedule();
    }

    @GetMapping ("/create")
    public String displayCreate(){
        return "create";
    }

    @PostMapping("/create")
    public String createSchedule(@RequestParam String thoigian, @RequestParam String thoiluong){
        Schedule schedule = new Schedule();
        schedule.setThoigian(LocalTime.parse(thoigian).toSecondOfDay());
        schedule.setThoiluong(LocalTime.parse("00:" + (thoiluong.length()==1?"0"+thoiluong:thoiluong)).toSecondOfDay());
        this.scheduleService.save(schedule);
        return "redirect:/time";
    }

    @GetMapping("/delete/{id}")
    public String deleteScheduleById(@PathVariable String id){
        this.scheduleService.deleteById(Integer.parseInt(id));
        return "redirect:/time";
    }

    @GetMapping("/history")
    @ResponseBody
    public List<HistoryDto> getListHistory(){
        return this.historyService.getListHistory();
    }
}
