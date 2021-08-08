package com.jh.cavy.common.utils.classScan;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassScanUtil {

    public static Set<Class<?>> getClassByAnnotateInClass(String pagePath, Class<? extends Annotation> annotation) {
        //入参 要扫描的包名
        Reflections f = new Reflections(pagePath);
        //入参 目标注解类
        return f.getTypesAnnotatedWith(annotation);
    }


    public static Set<Class<?>> getClassByAnnotateInFiled(String pagePath, Class<? extends Annotation> annotation) {
        Set<Class<?>> classes = ClassScannerUtils.searchClasses(pagePath);
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> aClass : classes) {
            //变量
            Field[] fs = aClass.getDeclaredFields();
            for (Field field : fs) {
                if (field.isAnnotationPresent(annotation)) {
                    log.info("有{}注释 类:{},变量:{}", annotation.getName(), aClass.getName(), field.getName());
                    result.add(aClass);
                }
            }
        }
        return result;
    }

    public static Set<Class<?>> getClassByAnnotateInMethod(String pagePath, Class<? extends Annotation> annotation) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = ClassScannerUtils.searchClasses(pagePath);
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> aClass : classes) {
            //方法
            Method[] ms = aClass.getDeclaredMethods();
            for (Method m : ms) {
                if (m.isAnnotationPresent(annotation)) {
                    log.info("有{}注释 类:{},方法:{}", annotation.getName(), aClass.getName(), m.getName());
                    result.add(aClass);
                }
            }
        }
        return result;
    }
}