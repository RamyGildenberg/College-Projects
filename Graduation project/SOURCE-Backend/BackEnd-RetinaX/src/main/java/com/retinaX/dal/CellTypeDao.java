package com.retinaX.dal;

import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.entities.function.Variable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CellTypeDao extends Neo4jRepository<CellType, Long> {

    Optional<CellType> findCellTypeByTransformType(CellTransformType transformType);

}
