package com.retinaX.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.utils.CloneManager;
import org.hibernate.validator.constraints.UniqueElements;
import org.neo4j.ogm.annotation.*;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.retinaX.entities.utils.RetinaXEntityLabels.CONNECTION;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FROM_CELL;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FUNCTION_INPUT_VARIABLE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.TO_CELL;

@NodeEntity(label = CONNECTION)
public class Connection implements Cloneable {

    @Id
    @GeneratedValue
    private Long id;

    private int delay;

    @UniqueElements
    @Relationship(type = FUNCTION_INPUT_VARIABLE)
    private Variable variable;

    @Relationship(type = FROM_CELL, direction = Relationship.INCOMING)
    @JsonBackReference
    private CellInstance fromCell;

    @Relationship(type = TO_CELL)
    private CellInstance toCell;


    public Connection() {
    }

    public Connection(int delay, CellInstance fromCell, CellInstance toCell, Variable variable) {
        setDelay(delay);
        setFromCell(fromCell);
        setToCell(toCell);
        setVariable(variable);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) throws InputMismatchException{
        if(delay < 0){
            throw new InputMismatchException("Delay can not be negative. got delay: " + delay);
        }
        this.delay = delay;
    }

    public CellInstance getFromCell() {
        return fromCell;
    }

    public void setFromCell(CellInstance fromCell) {
        this.fromCell = fromCell;
    }

    public CellInstance getToCell() {
        return toCell;
    }

    public void setToCell(CellInstance toCell) {
        this.toCell = toCell;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Connection clone(CellInstance fromCell){
        return new Connection(
                delay,
                fromCell,
                CloneManager.getClonedCell(toCell),
                CloneManager.getClonedVariable(variable));
//        try {
//            return (Connection) super.clone();
//        } catch (CloneNotSupportedException e) {
//            return new Connection(
//                    delay,
//                    fromCell,
//                    CloneManager.getClonedCell(toCell),
//                    variable.clone());
//        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return delay == that.delay &&
                Objects.equals(id, that.id) &&
                Objects.equals(variable, that.variable) &&
                Objects.equals(fromCell, that.fromCell) &&
                Objects.equals(toCell, that.toCell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, delay, variable, fromCell, toCell);
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", delay=" + delay +
                ", variable=" + variable +
                ", fromCell=" + fromCell +
                ", toCell=" + toCell +
                '}';
    }
}
