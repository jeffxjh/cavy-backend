package com.jh.cavy.groovy;

import groovy.lang.GroovyClassLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        applicationContext = context;
    }

    public static void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(beanName.getClass());
        context.refresh();
    }

    /**
     * 动态注入单例bean实例
     *
     * @param beanName        bean名称
     * @param singletonObject 单例bean实例
     * @return 注入实例
     */
    public static Object registerSingletonBean(String beanName, Object singletonObject)
    {

        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getAutowireCapableBeanFactory();

        //动态注册bean.
        defaultListableBeanFactory.registerSingleton(beanName, singletonObject);

        //获取动态注册的bean.
        return configurableApplicationContext.getBean(beanName);
    }

    final static String TEST_GROOVY_BEAN = """
            package com.jh.drb;
            
            import org.springframework.stereotype.Service;
            
            @Service
            public class UserServiceImpl implements UserService
            {
                @Override
                public String getName()
                {
                    return "运行时替换的实现";
                }
            }
            
            """;



    public static void registerBeanFormGroovy()
    {
        try (GroovyClassLoader groovyClassLoader=new GroovyClassLoader()) {
            Class<?> clazz =  groovyClassLoader.parseClass(TEST_GROOVY_BEAN);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            autowireCapableBeanFactory.initializeBean(beanDefinition, "userServiceImpl");
            //autowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(beanDefinition, "userServiceImpl");
            //DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getParentBeanFactory();
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            beanFactory.removeBeanDefinition("userServiceImpl");
            beanFactory.registerBeanDefinition("userServiceImpl", beanDefinition);
            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                System.out.println(beanDefinitionName);
            }
        }
        catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}