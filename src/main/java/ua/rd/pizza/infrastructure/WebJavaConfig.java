package ua.rd.pizza.infrastructure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ua.rd.pizza.controller")
public class WebJavaConfig {
}
