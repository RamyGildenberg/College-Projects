package com.retinaX.services.function;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;

import java.util.List;
import java.util.Map;

public interface FunctionInvocation {

    void parse(String expression, List<Variable> variables);

    CellData invoke(Map<Variable, CellData> inputParams);
}
