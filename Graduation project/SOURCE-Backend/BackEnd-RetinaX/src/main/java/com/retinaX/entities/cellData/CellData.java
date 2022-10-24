package com.retinaX.entities.cellData;

import java.util.stream.Stream;

public class CellData {

    private static final int DEFAULT_MAX_NUM_OF_BITS = 8;

    private double value;

    public CellData(double value){
        setValue(value);
    }

    private void setValue(double value) {
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    /**
     * Assumes value in range [0, 1]
     */

    public  Integer[] toDigitalArrayOfBits(int numOfBits){
        int digitalValue = (int)(value * ((int)Math.pow(2, numOfBits)));

        return Stream.of(Integer.toBinaryString(digitalValue)
                .split(""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

    }


    public  Integer[] toDigitalArrayOfBits(){
        return toDigitalArrayOfBits(DEFAULT_MAX_NUM_OF_BITS);
    }

    @Override
    public String toString() {
        return value + "";
    }
}
