package com.jh.cavy.common.enums;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Import({EnumRegistrar.class})
public @interface EnumComponentScan {
    String[] value() default {};
    String[] basePackages() default {};
}