package com.retinaX.dal.utils;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Id;


@NodeEntity(label = "IdGenerator")
public class IdGeneratorEntity {

    @Id
    @GeneratedValue
    private Long id;

    public IdGeneratorEntity(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
