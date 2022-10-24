package com.retinaX.dal;

import com.retinaX.entities.CellInstance;
import com.retinaX.entities.Connection;

import com.retinaX.entities.function.Variable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.retinaX.entities.utils.RetinaXEntityLabels.CELL_INSTANCE;
import static com.retinaX.entities.utils.RetinaXEntityLabels.CONNECTION;
import static com.retinaX.entities.utils.RetinaXEntityLabels.VARIABLE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FROM_CELL;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.FUNCTION_INPUT_VARIABLE;
import static com.retinaX.entities.utils.RetinaXRelationshipTypes.TO_CELL;


public interface ConnectionDao extends Neo4jRepository<Connection, Long> {


    @Depth(value = 4)
    @Query("MATCH (cell:"+ CELL_INSTANCE +")<-[" + TO_CELL + "]-(connection:" + CONNECTION + ") " +
            ",(var:"+ VARIABLE +")<-[" + FUNCTION_INPUT_VARIABLE + "]-(connection:" + CONNECTION + ") " +
            ",(fromCell:"+ CELL_INSTANCE +")-[" + FROM_CELL + "]->(connection:" + CONNECTION + ") " +
            "WHERE id(cell) = $toCell"+
            "RETURN connection, var, fromCell, cell")
    List<Connection> getConnectionsByToCell(@Param("toCell") CellInstance toCell);


    @Query("MATCH (toCell:"+ CELL_INSTANCE +")<-[" + TO_CELL + "]-(connection:" + CONNECTION + ") ," +
            "(var:"+ VARIABLE +")<-[" + FUNCTION_INPUT_VARIABLE + "]-(connection:" + CONNECTION + ") " +
            "WHERE id(toCell) = $toCellParam AND id(var) = $varParam "+
            "DETACH DELETE connection")
    void deleteConnectionByToCellAndAndConnectionVariable(@Param("toCellParam") CellInstance toCell,@Param("varParam") Variable variable);

    @Query("MATCH (toCell:"+ CELL_INSTANCE +")<-[" + TO_CELL + "]-(connection:" + CONNECTION + ") " +
            ",(connection:" + CONNECTION + ") <-[" + FROM_CELL + "]-(fromCell:"+ CELL_INSTANCE +")" +
            "WHERE ID(toCell) = $cellInstance OR ID(fromCell) = $cellInstance"+
            "DETACH DELETE connection")
    void deleteConnectionByToCellOrFromCell(@Param("cellInstance") CellInstance cellInstance);

}
