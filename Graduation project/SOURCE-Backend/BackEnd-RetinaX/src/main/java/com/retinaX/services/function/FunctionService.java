package com.retinaX.services.function;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Function;
import com.retinaX.entities.function.Variable;


import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


public class FunctionService {

    private Function function;
    private FunctionInvocation functionInvocation;

    public FunctionService(Function function, FunctionInvocation functionInvocation){
        this.function = function;
        this.functionInvocation = functionInvocation;
        this.functionInvocation.parse(function.getExpression(), new ArrayList<>(function.getVariables()));
    }

    public CellData invoke(Map<Variable, CellData> inputParameters){
        return functionInvocation.invoke(inputParameters);
    }

    public boolean hasFunctionVariable(Variable variable){
        return function.getVariables().contains(variable);
    }

    public boolean hasFunctionVariable(String variableName){
        return function.getVariables().stream().anyMatch(variable -> variable.getVariableName().equals(variableName));
    }

    public Variable getVariable(String variableName) throws NoSuchElementException{
       return getVariable(function, variableName);
    }

    public static Variable getVariable(Function function, String variableName) throws NoSuchElementException{
        Optional<Variable> variableOptional = function.getVariables().stream().filter(variable -> variable.getVariableName().equals(variableName)).findFirst();
        if(!variableOptional.isPresent()){
            throw new NoSuchElementException("No Variable named: " + variableName);
        }
        return variableOptional.get();
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public void setFunctionInvocation(FunctionInvocation functionInvocation) {
        this.functionInvocation = functionInvocation;
    }
}
