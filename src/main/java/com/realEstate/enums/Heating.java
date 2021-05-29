package com.realEstate.enums;

public enum Heating {
    HEAT_1("централізоване"),
    HEAT_2("індивідуальне"),
    HEAT_3("без опалення");

    private String value;

    public String getValue() {
        return value;
    }

    private Heating(String value) {
        this.value = value;
    }
}
