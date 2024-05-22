package org.isec.pa.ecossistema.utils;

public enum Cores {
    RED(0.7),
    GREEN(1.7);

    private final double code;

    Cores(double code) {
        this.code = code;
    }

    public double getCode(){
        return code;
    }
}
