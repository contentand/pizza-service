package ua.rd.pizza.domain;

import javax.persistence.*;

@Entity @Table(name = "customers")
public class Customer {
    @TableGenerator(name = "ids", table = "ids", pkColumnValue = "customers")
    @Id @GeneratedValue(generator = "ids")
    private Integer id;
    private String name;
    private String address;
    @ManyToOne
    private MemberCard memberCard;

    public Customer() {
    }

    public Customer(Integer id, String name, String address, MemberCard memberCard) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.memberCard = memberCard;
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

    public MemberCard getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCard memberCard) {
        this.memberCard = memberCard;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
