package me.mingshan.service.annotation;

import java.lang.annotation.*;

/**
 * @Author: mingshan
 * @Date: Created in 19:43 2018/6/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisCache {
    @SuppressWarnings("rawtypes")
    Class type();
    int expire() default -1;      //缓存多少秒,默认无限期
}