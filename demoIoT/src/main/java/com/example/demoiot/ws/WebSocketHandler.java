package com.example.demoiot.ws;


import com.example.demoiot.dto.DataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class WebSocketHandler  {
    private final SimpMessageSendingOperations messageTemplates;

    @Autowired
    public WebSocketHandler(SimpMessageSendingOperations messageTemplates){
        this.messageTemplates = messageTemplates;
    }

    public void publishData(DataDto newData){
        System.out.println("Websocket update new data");
        this.messageTemplates.convertAndSend("/topic/data-receive", newData);
    }
}
