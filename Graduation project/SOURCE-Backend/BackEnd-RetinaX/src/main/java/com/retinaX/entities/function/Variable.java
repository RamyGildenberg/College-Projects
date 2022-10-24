package com.retinaX.entities.function;



import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.HashSet;
import java.util.Objects;

import static com.retinaX.entities.utils.RetinaXEntityLabels.VARIABLE;


@NodeEntity(label = VARIABLE)
public class Variable implements Cloneable {

    @Id
    @GeneratedValue
    private Long id;

    private String variableName;


    public Variable(){

    }

    public Variable(String variableName){
        setVariableName(variableName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Variable clone() {
        try {
            Variable cloned = (Variable) super.clone();
            cloned.setId(null);
            return cloned;
        } catch (CloneNotSupportedException e) {
            return new Variable(variableName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return variableName.equals(variable.variableName) && id.equals(variable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " : "+ variableName;
    }
}
