package ua.rd.pizza.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="empl")
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    Integer level;

    public Employee() {
    }

    public Employee(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
