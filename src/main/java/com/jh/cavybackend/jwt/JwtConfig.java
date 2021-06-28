package com.jh.cavybackend.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(value = {JwtProperties.class})
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfig
{
    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public JwtTokenUtil jwtTokenUtil()
    {
        return new JwtTokenUtil(this.jwtProperties);
    }
}
