package com.laterooms.typhoonweb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.mustache.MustacheTemplateLoader;
import org.springframework.web.servlet.view.mustache.MustacheViewResolver;

/**
 * Created by abraithwaite on 25/04/2014.
 */
@EnableWebMvc
@Configuration
public class TyphoonWeb extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }

    @Bean
    public MustacheTemplateLoader templateLoader() {
        MustacheTemplateLoader templateLoader = new MustacheTemplateLoader();
        return templateLoader;
    }

    @Bean
    public ViewResolver viewResolver() {
        MustacheViewResolver mustacheViewResolver = new MustacheViewResolver();
        mustacheViewResolver.setTemplateLoader(templateLoader());
        mustacheViewResolver.setPrefix("/templates/");
        mustacheViewResolver.setSuffix(".html");
        mustacheViewResolver.setCache(false);
        mustacheViewResolver.setContentType("text/html;charset=utf-8");
        return mustacheViewResolver;
    }
}
