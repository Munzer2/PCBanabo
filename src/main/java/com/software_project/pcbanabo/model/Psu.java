package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "psu")
public class Psu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    private String model_name;
    private String form_factor;
    private int wattage;
    @Column(name = "psu_length")
    private int psuLength;
    private int eps_connectors;
    private int pcie_8pin_connectors;
    private int pcie_16pin_connectors;
    private String certification;
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

    public String getForm_factor() {
        return form_factor;
    }

    public void setForm_factor(String form_factor) {
        this.form_factor = form_factor;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    public int getPsuLength() {
        return psuLength;
    }

    public void setPsuLength(int psu_length) {
        this.psuLength = psu_length;
    }

    public int getEps_connectors() {
        return eps_connectors;
    }

    public void setEps_connectors(int eps_connectors) {
        this.eps_connectors = eps_connectors;
    }

    public int getPcie_8pin_connectors() {
        return pcie_8pin_connectors;
    }

    public void setPcie_8pin_connectors(int pcie_8pin_connectors) {
        this.pcie_8pin_connectors = pcie_8pin_connectors;
    }

    public int getPcie_16pin_connectors() {
        return pcie_16pin_connectors;
    }

    public void setPcie_16pin_connectors(int pcie_16pin_connectors) {
        this.pcie_16pin_connectors = pcie_16pin_connectors;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
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
