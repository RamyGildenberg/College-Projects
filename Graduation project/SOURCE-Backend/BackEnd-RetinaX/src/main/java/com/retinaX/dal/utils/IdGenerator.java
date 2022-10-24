package com.retinaX.dal.utils;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

interface IdGenerator extends Neo4jRepository<IdGeneratorEntity, Long> {
}
