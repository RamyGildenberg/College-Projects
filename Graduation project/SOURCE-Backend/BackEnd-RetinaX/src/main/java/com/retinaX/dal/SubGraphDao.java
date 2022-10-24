package com.retinaX.dal;

import com.retinaX.entities.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.retinaX.entities.utils.RetinaXEntityLabels.*;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.*;

public interface SubGraphDao extends Neo4jRepository<SubGraphInstance, Long> {

    // Optional<SubGraphInstance> findCellTypeByTransformType(CellTransformType transformType);
   // Optional<SubGraphInstance> findCellTypeByTransformType(CellTransformType transformType);
    @Query
        ("MATCH p=(type:"+SUB_GRAPH_INSTANCE+")-[*]-() "+
                "WHERE type.name=$subGraphName "+
                "RETURN p"
        )
    List<SubGraphInstance> findAllByName(@Param("subGraphName") String name);

    //TODO MAKE SURE IT WORKS
    @Query
            ("MATCH p=(type:"+SUB_GRAPH_INSTANCE+")-[*]-() "+
                    "WHERE type.id=$subGraphName "+
                    "RETURN p"
            )
    SubGraphInstance findById(@Param("id") String name);

}
