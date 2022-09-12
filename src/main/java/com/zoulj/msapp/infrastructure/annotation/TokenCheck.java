package com.zoulj.msapp.infrastructure.annotation;

import java.lang.annotation.*;

/**
 * @author leonard
 * @Description
 * @date 2021-11-19 14:26
 */

@Target({ElementType.METHOD, ElementType.TYPE}) //注解作用目标在类和方法上
@Retention(RetentionPolicy.RUNTIME) //注解的生命周期
@Inherited
@Documented
public @interface TokenCheck {


    String name() default "";

    boolean check() default true;

}
