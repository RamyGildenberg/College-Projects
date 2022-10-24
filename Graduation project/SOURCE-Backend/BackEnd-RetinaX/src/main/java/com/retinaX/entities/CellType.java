package com.retinaX.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.retinaX.entities.function.Function;
import com.retinaX.services.utils.CloneManager;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Objects;

import static com.retinaX.entities.utils.RetinaXEntityLabels.CELL_TYPE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.CELL_TYPE_FUNCTION;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FROM_TYPE;

@NodeEntity(label = CELL_TYPE)
public class CellType implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private CellTransformType transformType;

    @Relationship(type = CELL_TYPE_FUNCTION, direction = Relationship.UNDIRECTED)
    private Function function;

    public CellType(String name, CellTransformType transformType, Function function) {
        setName(name);
        setTransformType(transformType);
        setFunction(function);
    }

    public CellType(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public CellType clone() {
        Function clonedFunc = CloneManager.getClonedFunction(function);
        return new CellType(name, transformType, clonedFunc);
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setTransformType(CellTransformType transformType) {
        this.transformType = transformType;
    }

    public CellTransformType getTransformType() {
        return transformType;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }

    public Integer getNumberOfInputs() {
        if(function != null){
          return function.getNumberOfVariables();
        }
        return null;
    }

    public CellTransformType.Type getInputType(){
        if(transformType != null){
            return transformType.getInput();
        }
        return null;

    }

    public CellTransformType.Type getOutputType(){
        //return transformType.getOutput();
        if(transformType != null){
            return transformType.getOutput();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellType cellType = (CellType) o;
        return id.equals(cellType.id) &&
                name.equals(cellType.name) &&
                transformType.equals(cellType.transformType) &&
                function.equals(cellType.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, transformType, function);
    }

    @Override
    public String toString() {
        return "CellType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", transformType=" + transformType +
                ", function=" + function +
                '}';
    }
}
