package com.example.demoiot.Controller;

import com.example.demoiot.dto.DataDto;
import com.example.demoiot.gateway.MqttGateway;
import com.example.demoiot.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebsocketController {
    @Autowired
    private MqttGateway mqttGateway;

    @MessageMapping("/data.humidity-percentage")
    @SendTo("/topic/set-time")
    public ResponseEntity<Object> humidityMessage(@Payload String data){
        System.out.println(data);
        this.mqttGateway.sendToMqtt(data, "setTime");
        return ResponseEntity.ok("ok");
    }

}
