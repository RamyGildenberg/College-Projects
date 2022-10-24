package com.retinaX.dal;

import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.entities.function.Function;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface FunctionDao extends Neo4jRepository<Function, Long> {
    Optional<Function> findFunctionById(Function functionId);
//    Optional<CellType> findCellTypeByTransformType(CellTransformType transformType);

}
