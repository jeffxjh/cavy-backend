package com.jh.cavy.manage.config;

import com.jh.cavy.manage.domain.Config;
import com.jh.cavy.manage.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Configuration
public class SystemConfig {

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private ConfigService service;

    @PostConstruct
    public void initDatabasePropertySourceUsage() {
        // 获取系统属性集合
        MutablePropertySources propertySources = environment.getPropertySources();

        try {
            // 从数据库获取自定义变量列表
            Map<String, String> collect = service.getAll().stream().collect(Collectors.toMap(Config::getCode, Config::getCodeValue));

            // 将转换后的列表加入属性中
            Properties properties = new Properties();
            properties.putAll(collect);

            // 将属性转换为属性集合，并指定名称
            PropertiesPropertySource constants = new PropertiesPropertySource("system-config", properties);

            // 定义寻找属性的正则，该正则为系统默认属性集合的前缀
            Pattern p = Pattern.compile("^applicationConfig.*");

            // 接收系统默认属性集合的名称
            String name = null;
            // 标识是否找到系统默认属性集合
            boolean flag = false;

            // 遍历属性集合
            for (PropertySource<?> source : propertySources) {
                // 正则匹配
                if (p.matcher(source.getName()).matches()) {
                    // 接收名称
                    name = source.getName();
                    // 变更标识
                    flag = true;

                    break;
                }
            }

            if (flag) {
                // 找到则将自定义属性添加到该属性之前
                propertySources.addBefore(name, constants);
            } else {
                // 没找到默认添加到第一位
                propertySources.addFirst(constants);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
