package me.mingshan.hnote.web.config;

import me.mingshan.hnote.web.authorization.interceptor.AuthorizationInterceptor;
import me.mingshan.hnote.web.authorization.resolver.CurrentUserMethodArgumentResolver;
import me.mingshan.hnote.web.interceptor.ProcessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * The configuration of spring-mvc.
 * @Author: Minsghan
 * @Date: Created in 22:07 2017/10/14
 */
@Configuration
@EnableWebMvc
//指明controller所在的包名
@ComponentScan(basePackages = {"me.mingshan.hnote.web.controller"})
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Autowired
    private ProcessInterceptor processInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(processInterceptor);
        registry.addInterceptor(authorizationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    // swagger与fastjson不兼容
/*    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converters.add(converter);
    }*/
}
