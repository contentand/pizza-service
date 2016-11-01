package ua.rd.pizza.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.repository.CustomerRepository;

@Configuration
@ComponentScan(basePackages = {"ua.rd.pizza.domain.product", "ua.rd.pizza.domain.other", "ua.rd.pizza.service"})
@Import(value = {RepoJavaConfig.class, DiscountJavaConfig.class})
public class AppJavaConfig {

    @Autowired
    CustomerRepository customerRepository;

    @Bean // TODO : Remove and reconfigure once MVC + Security configured
    public Customer customer() {
        Customer ctm = new Customer();
        ctm.setName("Peter");
        ctm.setAddress("Great St. 2");
        return customerRepository.save(ctm);
    }

}
