package com.jh.cavy.groovy;

import com.jh.cavy.groovy.service.ScriptInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableAutoConfiguration
public class GroovyApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(GroovyApplication.class, args);
    }

    //@Qualifier(value = "scriptInterfaceImpl")
    @Autowired
    ScriptInterface scriptInterface;
    @Autowired
    private GroovyLoader groovyLoader;
    @Autowired
    private  ApplicationContext applicationContext;
    @RequestMapping("/loadBean")
    public String loadBean(@RequestBody LoadBeanInfo loadBeanInfo) {
        String beanName = loadBeanInfo.getBeanName();
        String script = loadBeanInfo.getScript();
        Object instance = groovyLoader.load(beanName, script);
        ScriptInterface loader = (ScriptInterface) instance;
        return loader.getName();
    }

    @RequestMapping("/getName")
    public String getName(@RequestBody LoadBeanInfo loadBeanInfo) {
        String[] names = applicationContext.getBeanDefinitionNames();
        int index = 1;
        for (String name : names) {
        log.debug("{}: {}", index++ , name );
        }
        log.debug("项目启动 容器注入javaBean:{}个.", names.length);
        return scriptInterface.getName();
    }

    @RequestMapping("/groovy")
    public String test(@RequestBody LoadBeanInfo loadBeanInfo) {
        String beanName = loadBeanInfo.getBeanName();
        String scriptBase64 = loadBeanInfo.getScriptBase64();
        Boolean existBeanName = groovyLoader.existBeanName(beanName, scriptBase64);
        if (existBeanName) {
            throw new RuntimeException("beanName 已存在，请重新传参beanName：" + beanName);
        }
        Object instance = groovyLoader.getBean(beanName, scriptBase64);
        ScriptInterface loader = (ScriptInterface) instance;
        return loader.getName();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        scriptInterface.exec();
    }
}
