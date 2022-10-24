package com.retinaX.coreAPI.buildNetworkAPI.requests;

import java.util.Set;

public class CreateFunctionRequest {
    private String expression;
    private Set<String> variables;

    public CreateFunctionRequest() {
    }

    public CreateFunctionRequest(String expression, Set<String> variables) {
        setExpression(expression);
        setVariables(variables);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Set<String> getVariables() {
        return variables;
    }

    public void setVariables(Set<String> variables) {
        this.variables = variables;
    }
}
