package me.mingshan.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisCache {
    @SuppressWarnings("rawtypes")
    Class type();
    int expire() default -1;      //缓存多少秒,默认无限期  
}
