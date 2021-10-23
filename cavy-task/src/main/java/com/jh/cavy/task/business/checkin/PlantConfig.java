package com.jh.cavy.task.business.checkin;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@PropertySource("classpath:plant.properties")
@ConfigurationProperties(prefix = "plants")
public class PlantConfig {
    private List<Plant> plant;

}
