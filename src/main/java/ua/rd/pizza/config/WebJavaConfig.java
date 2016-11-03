package ua.rd.pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import ua.rd.pizza.infrastructure.web.MyHandlerMapping;
import ua.rd.pizza.infrastructure.web.UrlHandlerMapping;

@Configuration
@ComponentScan(basePackages = "ua.rd.pizza.controllerrest")
@EnableWebMvc
public class WebJavaConfig {

    @Bean("HandlerMappingStrategy")
    MyHandlerMapping getHandlerMapping() {
        return new UrlHandlerMapping();
    }
}
