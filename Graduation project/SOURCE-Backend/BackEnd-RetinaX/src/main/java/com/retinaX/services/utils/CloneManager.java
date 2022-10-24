package com.retinaX.services.utils;

import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellType;
import com.retinaX.entities.SubGraphInstance;
import com.retinaX.entities.function.Function;
import com.retinaX.entities.function.Variable;

import java.util.HashMap;

public class CloneManager {
    private static HashMap<Long, CellInstance> clonedCells = new HashMap<Long, CellInstance>();
    private static HashMap<Long, Function> clonedFunctions = new HashMap<Long, Function>();
    private static HashMap<Long, Variable> clonedVariables = new HashMap<Long, Variable>();
    private static HashMap<Long, CellType> clonedCellTypes = new HashMap<Long, CellType>();
    private static HashMap<Long, SubGraphInstance> clonedSubGraphInstance = new HashMap<Long, SubGraphInstance>();


    public static CellInstance getClonedCell(CellInstance cell) {
        if (!clonedCells.containsKey(cell.getId())) {
            clonedCells.put(cell.getId(), cell.clone());
        }
        return clonedCells.get(cell.getId());
    }

    public static Function getClonedFunction(Function func) {
        if (!clonedFunctions.containsKey(func.getId())) {
            clonedFunctions.put(func.getId(), func.clone());
        }

        return clonedFunctions.get(func.getId());
    }

    public static Variable getClonedVariable(Variable var) {
        if (!clonedVariables.containsKey(var.getId())) {
            clonedVariables.put(var.getId(), var.clone());
        }

        return clonedVariables.get(var.getId());
    }
    public static CellType getClonedCellType(CellType type) {
        if (!clonedCellTypes.containsKey(type.getId())) {
            clonedCellTypes.put(type.getId(), type.clone());
        }

        return clonedCellTypes.get(type.getId());
    }
    public static SubGraphInstance getClonedSubGraphInstance(SubGraphInstance graphInstance) {
        if (!clonedSubGraphInstance.containsKey(graphInstance.getId())) {
            clonedSubGraphInstance.put(graphInstance.getId(), graphInstance.clone());
        }

        return clonedSubGraphInstance.get(graphInstance.getId());
    }
    public static void reset() {
        clonedCells = new HashMap<>();
        clonedFunctions = new HashMap<>();
        clonedVariables = new HashMap<>();
        clonedCellTypes= new HashMap<>();
        clonedSubGraphInstance=new HashMap<>();
    }
}
