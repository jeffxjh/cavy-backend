package com.jh.cavymanage.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JwtEnvironmentPostProcessor
        implements EnvironmentPostProcessor
{
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication)
    {
        ClassPathResource classPathResource = new ClassPathResource("conf/jwt.properties");
        try
        {
            InputStream inputStream = classPathResource.getInputStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            PropertiesPropertySource propertySource = new PropertiesPropertySource("jwtProperties", properties);
            configurableEnvironment.getPropertySources().addLast(propertySource);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}