package com.virtuslab.internship.product;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Product {
    @javax.persistence.Id
    private @Id @GeneratedValue Long id;
    private String name;
    private Type type;
    BigDecimal price;
    public Product(String name, Type type, BigDecimal price){
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public enum Type {
        DAIRY, FRUITS, VEGETABLES, MEAT, GRAINS
    }

    public Product() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getName(){
        return name;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }



}
