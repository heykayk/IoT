package com.example.demoiot.configuration;//package com.example.demo.configuration;

import com.example.demoiot.dto.DataDto;
import com.example.demoiot.model.Data;
import com.example.demoiot.service.DataService;
import com.example.demoiot.ws.WebSocketHandler;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;

@Configuration
public class MqttConfig
{

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory()
    {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        try {
            String serverUri = String.format("tcp://%s:1883", Inet4Address.getLocalHost().getHostAddress());
            System.out.println("địa chỉ ip là : " + Inet4Address.getLocalHost().getHostAddress().toString());
            options.setServerURIs(new String[]{serverUri});
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        options.setUserName("admin");
        String password = "123456";
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);

        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel()
    {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound()
    {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("serverIn", mqttPahoClientFactory(), "#");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler()
    {
        return new MessageHandler()
        {
            @Autowired
            private WebSocketHandler webSocketHandler;
            @Autowired
            private DataService dataService;
            @Override
            public void handleMessage(Message<?> message) throws MessagingException
            {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                if(topic.equals("dataSensorOutput"))
                {
                    Gson gson = new Gson();
                    Data data = gson.fromJson(message.getPayload().toString(), Data.class);
                    data.setTimeNow(new Date().getTime());
                    this.dataService.saveData(data);
                    this.webSocketHandler.publishData(new DataDto(data));
                }
                if(topic.equals("setTime"))
                {
                    System.out.println("client gui du lieu");
                }
                System.out.println(message.getPayload());
            }
        };
    }

    @Bean
    public MessageChannel mqttOutBoundChannel()
    {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutBoundChannel")
    public MessageHandler mqttOutBound()
    {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("serverOut", mqttPahoClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("inTopic");
        return messageHandler;
    }
}

