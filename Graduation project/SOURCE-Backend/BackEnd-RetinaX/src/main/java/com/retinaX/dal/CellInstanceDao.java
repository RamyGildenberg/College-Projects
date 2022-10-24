package com.retinaX.dal;

import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellType;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.retinaX.entities.utils.RetinaXEntityLabels.CELL_INSTANCE;
import static com.retinaX.entities.utils.RetinaXEntityLabels.CELL_TYPE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FROM_TYPE;


public interface CellInstanceDao extends Neo4jRepository<CellInstance, Long> {

    @Query( "MATCH (type:" + CELL_TYPE + ")<-[" + FROM_TYPE + "]-(instance:" + CELL_INSTANCE + ") " +
            "WHERE ID(type) = $cellTypeId " +
            "RETURN instance")
    List<CellInstance> findAllByCellType(@Param("cellTypeId") Long cellTypeId);

}
