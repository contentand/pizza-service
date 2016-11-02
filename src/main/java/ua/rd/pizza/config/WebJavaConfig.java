package ua.rd.pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.rd.pizza.infrastructure.web.MyHandlerMapping;
import ua.rd.pizza.infrastructure.web.UrlHandlerMapping;

@Configuration
@ComponentScan(basePackages = "ua.rd.pizza.controller")
public class WebJavaConfig {

    @Bean("HandlerMappingStrategy")
    MyHandlerMapping getHandlerMapping() {
        return new UrlHandlerMapping();
    }
}
