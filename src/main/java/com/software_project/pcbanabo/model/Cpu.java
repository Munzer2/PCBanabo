package com.software_project.pcbanabo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "cpu",
    uniqueConstraints=@UniqueConstraint(columnNames = "model_name")
    )
public class Cpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    @Column(name = "model_name", nullable = false)
    private String modelName;
    private String sku_number;
    private String socket;
    private int tdp;
    private String cache_size;
    private boolean overclockable;
    private double average_price;
    private String official_product_url;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModel_name() {
        return modelName;
    }

    public void setModel_name(String model_name) {
        this.modelName = model_name;
    }

    public String getSku_number() {
        return sku_number;
    }

    public void setSku_number(String sku_number) {
        this.sku_number = sku_number;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public int getTdp() {
        return tdp;
    }

    public void setTdp(int tdp) {
        this.tdp = tdp;
    }

    public String getCache_size() {
        return cache_size;
    }

    public void setCache_size(String cache_size) {
        this.cache_size = cache_size;
    }

    public boolean isOverclockable() {
        return overclockable;
    }

    public void setOverclockable(boolean overclockable) {
        this.overclockable = overclockable;
    }

    public double getAverage_price() {
        return average_price;
    }

    public void setAverage_price(double average_price) {
        this.average_price = average_price;
    }

    public String getOfficial_product_url() {
        return official_product_url;
    }

    public void setOfficial_product_url(String official_product_url) {
        this.official_product_url = official_product_url;
    }
}
