package com.d.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LockEvict {
    /* 锁的value值，支持spel表达式 */
    String value() default "";

    /* 锁的value值，支持spel表达式 */
    String key() default "";
}
