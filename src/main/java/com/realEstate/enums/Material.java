package com.realEstate.enums;

public enum Material {
    MATERIAL_1("цегла"),
    MATERIAL_2("панель"),
    MATERIAL_3("моноліт"),
    MATERIAL_4("піноблок"),
    MATERIAL_5("газоблок"),
    MATERIAL_6("ракушняк"),
    MATERIAL_7("дерево");


    private String value;

    public String getValue() {
        return value;
    }

    private Material(String value) {
        this.value = value;
    }
}
