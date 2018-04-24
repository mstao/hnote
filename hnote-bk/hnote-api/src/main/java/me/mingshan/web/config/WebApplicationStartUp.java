package me.mingshan.web.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * The configuration of web application.
 * @Author: Minsghan
 * @Date: Created in 21:45 2017/10/14
 */
public class WebApplicationStartUp implements WebApplicationInitializer {
    private static final String SERVLET_NAME = "Spring-mvc";

    // 5 Mb
    private static final long MAX_FILE_UPLOAD_SIZE = 1024 * 1024 * 5;
    // After 1Mb
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024;
    // No request size limit
    private static final long MAX_REQUEST_SIZE = -1L;

    /**
     * Loads configuration when web application starts up.
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        this.addServlet(servletContext);
    }

    private void addServlet(ServletContext servletContext) {
        // 构建一个application context
        AnnotationConfigWebApplicationContext webContext = createWebContext(MvcConfig.class);
        // 注册spring mvc 的 servlet
        Dynamic dynamic = servletContext.addServlet(SERVLET_NAME, new DispatcherServlet(webContext));
        // 添加springMVC 允许访问的Controller后缀
        // 全部通过请用 “/”
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);
        dynamic.setMultipartConfig(new MultipartConfigElement(null, MAX_FILE_UPLOAD_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD));
    }

    /**
     * 通过自定义的配置类来实例化一个Web Application Context
     *
     * @param annotatedClasses
     * @return AnnotationConfigWebApplicationContext
     */
    private AnnotationConfigWebApplicationContext createWebContext(Class<?>... annotatedClasses) {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(annotatedClasses);

        return webContext;
    }
}
