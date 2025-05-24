package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ssd")
public class Ssd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    private String model_name;
    private String capacity;
    private String form_factor;
    private String pcie_gen;
    private Integer seq_read;
    private Integer seq_write;
    private Boolean dram_cache;
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getForm_factor() {
        return form_factor;
    }

    public void setForm_factor(String form_factor) {
        this.form_factor = form_factor;
    }

    public String getPcie_gen() {
        return pcie_gen;
    }

    public void setPcie_gen(String pcie_gen) {
        this.pcie_gen = pcie_gen;
    }

    public Integer getSeq_read() {
        return seq_read;
    }

    public void setSeq_read(Integer seq_read) {
        this.seq_read = seq_read;
    }

    public Integer getSeq_write() {
        return seq_write;
    }

    public void setSeq_write(Integer seq_write) {
        this.seq_write = seq_write;
    }

    public Boolean getDram_cache() {
        return dram_cache;
    }

    public void setDram_cache(Boolean dram_cache) {
        this.dram_cache = dram_cache;
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
