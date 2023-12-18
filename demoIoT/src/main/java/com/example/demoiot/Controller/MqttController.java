package com.example.demoiot.Controller;

import com.example.demoiot.gateway.MqttGateway;
import com.example.demoiot.model.Data;
import com.example.demoiot.repository.DataRepository;
import com.example.demoiot.service.DataService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MqttController {
    private final MqttGateway mqttGateway;

    private final DataService dataService;

    @Autowired
    public MqttController(MqttGateway mqttGateway, DataService dataService){
        this.mqttGateway= mqttGateway;
        this.dataService = dataService;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> publish(@RequestBody String mqttMessage){
        try{
            JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
            mqttGateway.sendToMqtt(convertObject.get("message").toString(), "setTime");

            return ResponseEntity.ok("Success");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }

}
