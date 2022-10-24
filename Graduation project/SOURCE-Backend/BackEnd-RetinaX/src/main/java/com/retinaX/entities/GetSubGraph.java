package com.retinaX.entities;


import com.retinaX.entities.GetSubGraph;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.retinaX.entities.utils.RetinaXEntityLabels.FUNCTION;
import static com.retinaX.entities.utils.RetinaXEntityLabels.SUB_GRAPH_INSTANCE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FUNCTION_VARS;

@NodeEntity(label = SUB_GRAPH_INSTANCE)

public class GetSubGraph {
    @Id
    @GeneratedValue
    private Long id;
    private Set<CellInstance> cellInstanceList;
    private String name;




    public GetSubGraph(){

        cellInstanceList = new HashSet<>();
    }

    public GetSubGraph(String name, Set<CellInstance> cellInstanceList){
        this.name = name;
        this.cellInstanceList = cellInstanceList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CellInstance> getCellInstanceList() {
        return cellInstanceList;
    }

    public void setCellInstanceList(Set<CellInstance> cellInstanceList) {
        this.cellInstanceList = cellInstanceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetSubGraph that = (GetSubGraph) o;
        return Objects.equals(id, that.id) && Objects.equals(cellInstanceList, that.cellInstanceList) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cellInstanceList, name);
    }

    @Override
    public String toString() {
        return "GetSubGraph{" +
                "id=" + id +
                ", cellInstanceList=" + cellInstanceList +
                ", name='" + name + '\'' +
                '}';
    }
}

