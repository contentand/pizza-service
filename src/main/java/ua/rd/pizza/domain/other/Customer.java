package ua.rd.pizza.domain.other;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@TableGenerator(
        name = "CUSTOMER_GENERATOR",
        table = "IDS",
        pkColumnName = "NAME",
        pkColumnValue = "CUSTOMERS",
        valueColumnName = "VALUE",
        initialValue = 1,
        allocationSize = 1)
public class Customer {
    @Id @GeneratedValue(generator = "CUSTOMER_GENERATOR")
    private Integer id;
    private String name;
    private String address;

    public Customer() {}

    public Customer(Customer customer) {
        this.id = customer.id;
        this.name = customer.name;
        this.address = customer.address;
    }

    public Customer(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
