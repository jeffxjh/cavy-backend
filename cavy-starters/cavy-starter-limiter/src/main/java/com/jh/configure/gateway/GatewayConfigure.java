package com.jh.configure.gateway;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        //basePackages = { "com.jh.cavy.gateway" }
        //,excludeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.jh.cavy.gateway.limit"),
        //        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)}
                )
public class GatewayConfigure {
}
