package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ram")
public class Ram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    private String model_name;
    @Column(name = "mem_type")
    private String memType;
    private String capacity;
    private int speed;
    private String timings;
    private Boolean rgb;
    private double avg_price;
    private String official_product_url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getMemType() {
        return memType;
    }

    public void setMemType(String mem_type) {
        this.memType = mem_type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public Boolean getRgb() {
        return rgb;
    }

    public void setRgb(Boolean rgb) {
        this.rgb = rgb;
    }

    public double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }

    public String getOfficial_product_url() {
        return official_product_url;
    }

    public void setOfficial_product_url(String official_product_url) {
        this.official_product_url = official_product_url;
    }
}
