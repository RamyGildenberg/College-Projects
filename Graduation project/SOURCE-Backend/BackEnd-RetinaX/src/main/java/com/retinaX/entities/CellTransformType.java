package com.retinaX.entities;

public enum CellTransformType {


    ANALOG_TO_ANALOG(Type.ANALOG, Type.ANALOG),
    ANALOG_TO_DIGITAL(Type.ANALOG, Type.DIGITAL),
    DIGITAL_TO_ANALOG(Type.DIGITAL, Type.ANALOG),
    DIGITAL_TO_DIGITAL(Type.DIGITAL, Type.DIGITAL),
    INPUT_TO_ANALOG(Type.INPUT, Type.ANALOG);

    public enum Type{ DIGITAL, ANALOG, INPUT }
    private Type input;
    private Type output;

    CellTransformType(Type input, Type output){
        this.input = input;
        this.output = output;
    }

    public Type getInput() {
        return input;
    }

    public Type getOutput() {
        return output;
    }

    public boolean isCompatibleSource(CellTransformType source){
        return input.equals(source.output);
    }

    public boolean isCompatibleDestination(CellTransformType destination){
        return output.equals(destination.input);
    }

    public static boolean areCompatible(CellTransformType source, CellTransformType destination){
        return source.isCompatibleDestination(destination);
    }
}
