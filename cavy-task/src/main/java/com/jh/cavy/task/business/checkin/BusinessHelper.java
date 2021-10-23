package com.jh.cavy.task.business.checkin;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BusinessHelper {
    @Resource
    private PlantConfig plantConfig;
    @Value("${plant.isProxy:false}")
    private boolean isProxy;

    public void doThing() {
        List<Plant> plants = plantConfig.getPlant();
        CheckInHandle.invoker(plants,isProxy);
    }
}
