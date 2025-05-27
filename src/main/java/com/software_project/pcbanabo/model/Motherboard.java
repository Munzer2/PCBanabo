package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "motherboard")
public class Motherboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    private String model_name;
    private String chipset;
    private String socket;
    @Column(name = "form_factor")
    private String formFactor;
    private String mem_type;
    private int mem_slot;
    private Integer max_mem_speed;
    private int pcie_slot_x16;
    private int pcie_clot_x4;
    private int nvme_slots;
    private String lan_speed;
    private String wifi_version;
    private String bluetooth_version;
    private int usb_2_ports;
    private int usb_3_ports;
    private int usb_c_ports;
    private Integer thunderbolt_ports;
    private Integer max_power;
    private String opt_feature;
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

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public String getMem_type() {
        return mem_type;
    }

    public void setMem_type(String mem_type) {
        this.mem_type = mem_type;
    }

    public int getMem_slot() {
        return mem_slot;
    }

    public void setMem_slot(int mem_slot) {
        this.mem_slot = mem_slot;
    }

    public Integer getMax_mem_speed() {
        return max_mem_speed;
    }

    public void setMax_mem_speed(Integer max_mem_speed) {
        this.max_mem_speed = max_mem_speed;
    }

    public int getPcie_slot_x16() {
        return pcie_slot_x16;
    }

    public void setPcie_slot_x16(int pcie_slot_x16) {
        this.pcie_slot_x16 = pcie_slot_x16;
    }

    public int getPcie_clot_x4() {
        return pcie_clot_x4;
    }

    public void setPcie_clot_x4(int pcie_clot_x4) {
        this.pcie_clot_x4 = pcie_clot_x4;
    }

    public int getNvme_slots() {
        return nvme_slots;
    }

    public void setNvme_slots(int nvme_slots) {
        this.nvme_slots = nvme_slots;
    }

    public String getLan_speed() {
        return lan_speed;
    }

    public void setLan_speed(String lan_speed) {
        this.lan_speed = lan_speed;
    }

    public String getWifi_version() {
        return wifi_version;
    }

    public void setWifi_version(String wifi_version) {
        this.wifi_version = wifi_version;
    }

    public String getBluetooth_version() {
        return bluetooth_version;
    }

    public void setBluetooth_version(String bluetooth_version) {
        this.bluetooth_version = bluetooth_version;
    }

    public int getUsb_2_ports() {
        return usb_2_ports;
    }

    public void setUsb_2_ports(int usb_2_ports) {
        this.usb_2_ports = usb_2_ports;
    }

    public int getUsb_3_ports() {
        return usb_3_ports;
    }

    public void setUsb_3_ports(int usb_3_ports) {
        this.usb_3_ports = usb_3_ports;
    }

    public int getUsb_c_ports() {
        return usb_c_ports;
    }

    public void setUsb_c_ports(int usb_c_ports) {
        this.usb_c_ports = usb_c_ports;
    }

    public Integer getThunderbolt_ports() {
        return thunderbolt_ports;
    }

    public void setThunderbolt_ports(Integer thunderbolt_ports) {
        this.thunderbolt_ports = thunderbolt_ports;
    }

    public Integer getMax_power() {
        return max_power;
    }

    public void setMax_power(Integer max_power) {
        this.max_power = max_power;
    }

    public String getOpt_feature() {
        return opt_feature;
    }

    public void setOpt_feature(String opt_feature) {
        this.opt_feature = opt_feature;
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
