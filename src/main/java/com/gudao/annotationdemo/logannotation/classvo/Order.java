package com.gudao.annotationdemo.logannotation.classvo;

/**
 * Author : GuDao
 * 2020-12-07
 */

public class Order {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Order setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
