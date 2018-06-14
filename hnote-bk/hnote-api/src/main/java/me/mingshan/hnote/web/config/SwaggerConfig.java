package me.mingshan.hnote.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The configuration of swagger.
 * @Author: Minsghan
 * @Date: Created in 16:40 2017/10/3
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                //the unique identifier of this swagger group/configuration
                .groupName("mingshan.me")
                //Substitutes each generic class with it's direct parameterized type
                .genericModelSubstitutes(DeferredResult.class)
                //return ApiSelectorBuilder
                .select()
                //Predicate that matches RequestHandler with given base package name for the class of the handler method.
                .apis(RequestHandlerSelectors.basePackage("me.mingshan.hnote.web.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("H-note API")
                .description("The apis of h-note")
                .termsOfServiceUrl("http://mingshan.me")
                .version("1.0")
                .build();
    }

}
