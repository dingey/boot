package com.d.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LockMethod {

    /* 锁的value值，支持spel表达式 */
    //String value() default "";

    /* 锁的value值，支持spel表达式 */
    String key() default "";

    /**
     * 锁的value值，支持spel表达式
     */
    String condition() default "";

    /**
     * 提示内容
     */
    String message() default "";

    /**
     * 等待锁时长：毫秒
     */
    long waitTime() default 0L;

    /**
     * 锁住时长：毫秒
     */
    long lockTime() default 0L;
}