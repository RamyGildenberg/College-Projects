package com.retinaX.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.retinaX.services.utils.CloneManager;
import org.neo4j.ogm.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.retinaX.entities.utils.RetinaXEntityLabels.CELL_INSTANCE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FROM_CELL;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FROM_TYPE;


@NodeEntity(label = CELL_INSTANCE)
public class  CellInstance implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;
    //private Long graphId;

    @Relationship(type = FROM_TYPE, direction = Relationship.UNDIRECTED)
    private CellType cellType;

    @Relationship(type = FROM_CELL)
    @JsonManagedReference
    private List<Connection> connections;

    private Double xCoordinate;
    private Double yCoordinate;
    //private List<Long>listOfInputs;

    public CellInstance(){
    }


    public CellInstance(CellType cellType) {
        setCellType(cellType);
    }

    public CellInstance(CellType cellType, double x, double y) {
        setCellType(cellType);
        setxCoordinate(x);
        setyCoordinate(y);
    }

    @Override
    public CellInstance clone(){
        CellInstance result = new CellInstance(CloneManager.getClonedCellType(cellType),xCoordinate,yCoordinate);
        if(connections != null)
            result.setConnections(connections.stream().map(connection -> connection.clone(result)).collect(Collectors.toList()));
        return result;
//
//        try {
//            CellInstance cloned = (CellInstance) super.clone();
//            cloned.setId(null);
//            return cloned;
//        } catch (CloneNotSupportedException e) {
//            CellInstance result = new CellInstance(cellType.clone(),xCoordinate,yCoordinate);
//            result.setConnections(connections.stream().map(connection -> connection.clone(result)).collect(Collectors.toList()));
//            return result;
//        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellInstance that = (CellInstance) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CELL INSTANCE: "+getId() + " type:" + getCellType().getName();
    }

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
