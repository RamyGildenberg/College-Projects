package com.retinaX.dal;

import com.retinaX.entities.function.Variable;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VariableDao extends Neo4jRepository<Variable, Long> {

}
