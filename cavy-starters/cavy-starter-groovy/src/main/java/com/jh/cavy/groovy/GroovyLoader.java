package com.jh.cavy.groovy;

import com.google.common.base.Charsets;
import groovy.lang.GroovyClassLoader;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GroovyLoader implements ApplicationContextAware {

    private static final GroovyClassLoader groovyClassLoader;
    private ApplicationContext ctx;

    public static final String UTF_8 = "UTF-8";

    private static Map<String, String> extLoadMap = new ConcurrentHashMap<>(16);

    static {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setSourceEncoding(UTF_8);
        groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
    }


    public Boolean existBeanName(String beanName, String scriptBase64){
        if (!extLoadMap.containsKey(beanName)){
            return false;
        }
        String scriptBase64Value = extLoadMap.get(beanName);
        if (Objects.equals(scriptBase64, scriptBase64Value)){
            return false;
        }
        return true;
    }

    public Object getBean(String beanName, String scriptBase64){
        // 1. 尝试看 beanName 是否已经加载过，从 spring 容器中看
        Object bean = getBeanInner(beanName);
        //if (bean != null) {
        //    return bean;
        //}

        // 2. 针对java code进行编译，主要得到class返参结果
        Class<?> clz = complie(scriptBase64);

        // 3. 将class创建bean定义交给spring容器来实例化
        applyClz2Spring(beanName, clz);

        // 4. 再次从容器中获取beanName对应的实例返回
        bean = getBeanInner(beanName);
        extLoadMap.put(beanName, scriptBase64);
        return bean;
    }

    @SneakyThrows
    public Object load(String beanName, String script){
        Class<?> clz = groovyClassLoader.parseClass(new String(Base64.decodeBase64(script), Charsets.UTF_8));
        DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) ctx.getAutowireCapableBeanFactory();
        registry.destroySingleton(beanName);
        registry.registerSingleton(beanName,clz.newInstance());
        return this.ctx.getBean(beanName);
    }

    private Object getBeanInner(String beanName) {
        try {
            return this.ctx.getBean(beanName);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class<?> complie(String scriptBase64) {
        String script = new String(Base64.decodeBase64(scriptBase64), Charsets.UTF_8);
        return groovyClassLoader.parseClass(script);
    }

    private void applyClz2Spring(String beanName, Class<?> clz) {
        AbstractBeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(clz).getRawBeanDefinition();
        ((BeanDefinitionRegistry)((AbstractApplicationContext)ctx).getBeanFactory()).registerBeanDefinition(beanName, definition);
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}