package com.example.demoiot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Entity
@Table(name = "humidity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "humidity_percentage")
    private int humidityPercentage;

    @Column(name = "timestamp")
    private long timeNow;

    @Column(name = "status")
    private int status;
}
