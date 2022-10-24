package com.retinaX.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.retinaX.services.buildNetwork.BuildNetworkService;
import com.retinaX.services.utils.CloneManager;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.retinaX.entities.utils.RetinaXEntityLabels.SUB_GRAPH_INSTANCE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FROM_GRAPH;
@NodeEntity(label = SUB_GRAPH_INSTANCE)
public class SubGraphInstance implements Cloneable {

    @Relationship(type = FROM_GRAPH)
    @JsonManagedReference
    private List<CellInstance> cells =new ArrayList<>();

    public SubGraphInstance() {

    }

    public SubGraphInstance(List<CellInstance> cells) {
        this.cells = cells;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubGraphInstance clone(){
        SubGraphInstance result = new SubGraphInstance();
        result.setName(this.getName());
        List<CellInstance> clonedCells = new ArrayList<>();
        if(this.getCells() != null){
            for(CellInstance cell: this.getCells()){
                clonedCells.add(cell.clone());
            }
            result.setCells(clonedCells);
        }
        return result;
    }


    /*
public SubGraphInstance clone(){
    SubGraphInstance result = CloneManager.getClonedSubGraphInstance(this);
    result.setName(this.getName());
    List<CellInstance> clonedCells = new ArrayList<>();
    if(this.getCells() != null){
        for(CellInstance cell: this.getCells()){
            clonedCells.add(cell.clone());
        }
        result.setCells(clonedCells);
    }
    return result;
}
*/
    @Override
    public String toString() {
        return "SubGraphInstance{" +
                "cells=" + cells +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setCells(List<CellInstance> cells) {
        this.cells = cells;
    }
    public List<CellInstance> getCells() {
        return cells;
    }
}

