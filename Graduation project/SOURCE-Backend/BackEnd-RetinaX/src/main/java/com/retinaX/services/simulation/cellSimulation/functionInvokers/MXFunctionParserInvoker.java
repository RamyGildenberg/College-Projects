package com.retinaX.services.simulation.cellSimulation.functionInvokers;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.function.FunctionInvocation;

import java.util.*;
import java.util.stream.Collectors;

import org.mariuszgromada.math.mxparser.*;

public class MXFunctionParserInvoker implements FunctionInvocation {

    private List<Variable> variables;
    private Function mxFunction;
    private String functionHeader;

    @Override
    public void parse(String expression, List<Variable> variables) {
        setVariables(variables);
        functionHeader = buildFunctionHeader(variables);
        mxFunction = new Function(functionHeader + "=" + expression);
    }

    @Override
    public CellData invoke(Map<Variable, CellData> inputParams) {
        if(inputParams.size() != variables.size())
            throw new IllegalArgumentException("input parameters to function: " + mxFunction.getDescription());
        Argument[] arguments = initArguments(inputParams);
        Expression expression = new Expression(functionHeader, mxFunction);
        expression.addArguments(arguments);

        return new CellData(expression.calculate());
    }

    private Argument[] initArguments(Map<Variable, CellData> inputParams) {
        List<Argument> arguments = new ArrayList<>();
        inputParams.forEach((var, val) ->
                arguments.add(new Argument(var.getVariableName() + " = " + val.getValue()))
        );
        return arguments.toArray(new Argument[0]);
    }

    private String buildFunctionHeader(List<Variable> variables) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("f(");
        String varNames = variables.stream().map(Variable::getVariableName).collect(Collectors.joining(","));
        stringBuilder.append(varNames);
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    private void setVariables(List<Variable> variables) {
        this.variables = variables;
    }
}
