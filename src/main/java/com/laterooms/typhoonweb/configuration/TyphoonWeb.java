package com.laterooms.typhoonweb.configuration;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.jolokia.client.J4pClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.access.MBeanProxyFactoryBean;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;

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
    public ViewResolver viewResolver() {
        HandlebarsViewResolver handlebarsViewResolver = new HandlebarsViewResolver();
        handlebarsViewResolver.setPrefix("/templates/");
        handlebarsViewResolver.setSuffix(".hbs");
        handlebarsViewResolver.setCache(false);
        handlebarsViewResolver.setContentType("text/html;charset=utf-8");
        return handlebarsViewResolver;
    }

    @Bean
    public J4pClient jokoliaClient() {
        return new J4pClient("http://localhost:8080/typhoon/jolokia");
    }
}
