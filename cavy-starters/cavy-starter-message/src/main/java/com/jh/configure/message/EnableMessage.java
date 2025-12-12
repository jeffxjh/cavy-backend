package com.jh.configure.message;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MessageConfigure.class)
public @interface EnableMessage {
}
