import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retinaX.Application;
import com.retinaX.coreAPI.buildNetworkAPI.requests.ConnectCellsRequest;
import com.retinaX.dal.*;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.entities.Connection;
import com.retinaX.entities.function.Function;
import com.retinaX.entities.function.Variable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestConnectionDao {

    @LocalServerPort
    private String port;

    private ConnectionDao connectionDao;

    private CellTypeDao cellTypeDao;

    private CellInstanceDao cellInstanceDao;

    private FunctionDao functionDao;

    private VariableDao variableDao;

    @Before
    public void setUp() throws Exception {

    }

  //  @After
    public void tearDown() throws Exception {
        connectionDao.deleteAll();
        cellInstanceDao.deleteAll();
        variableDao.deleteAll();
        functionDao.deleteAll();
        cellTypeDao.deleteAll();
        connectionDao.deleteAll();
    }

    @Autowired
    public void setConnectionDao(ConnectionDao connectionDao) {
        this.connectionDao = connectionDao;
    }

    @Autowired
    public void setCellInstanceDao(CellInstanceDao cellInstanceDao) {
        this.cellInstanceDao = cellInstanceDao;
    }

    @Autowired
    public void setCellTypeDao(CellTypeDao cellTypeDao) {
        this.cellTypeDao = cellTypeDao;
    }

    @Autowired
    public void setFunctionDao(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }

    @Autowired
    public void setVariableDao(VariableDao variableDao) {
        this.variableDao = variableDao;
    }

    private Connection generateConnection(){
        Set<Variable> variables = Set.of("r", "s", "t").stream().map(Variable::new).collect(Collectors.toSet());
        CellType cellType = new CellType("CELL_TYPE", CellTransformType.ANALOG_TO_ANALOG, new Function("r + s / t", variables));

        cellType = cellTypeDao.save(cellType);

        CellInstance source = new CellInstance(cellType);
        CellInstance destination = new CellInstance(cellType);

        source = cellInstanceDao.save(source);
        destination =cellInstanceDao.save(destination);

        return new Connection(0, source, destination, variables.toArray(new Variable[0])[0]);
    }

    @Test
    public void findConnectionByToCell(){
        Connection connection = generateConnection();

        connection = connectionDao.save(connection);

        Iterable<Connection> connectionsResult = connectionDao.getConnectionsByToCell(connection.getToCell());
                //connectionDao.findAllByToCell(destination);

        List<Connection> listOfMatchingConnections = new ArrayList<>();

        connectionsResult.forEach(connection1 -> {
            Optional<Connection> optionalConnection = connectionDao.findById(connection1.getId(),3);
            optionalConnection.ifPresent(listOfMatchingConnections::add);
        });

        assertThat(listOfMatchingConnections).as("List of connections")
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .first().isEqualTo(connection);
    }

    @Test
    public void findConnectionByToCellWhenThereAreNoConnections(){
        Connection connection = generateConnection();

        Iterable<Connection> connectionsResult = connectionDao.getConnectionsByToCell(connection.getToCell());

        List<Connection> listOfMatchingConnections = new ArrayList<>();


        connectionsResult.forEach(connection1 -> {
            Optional<Connection> optionalConnection = connectionDao.findById(connection1.getId(),3);
            optionalConnection.ifPresent(listOfMatchingConnections::add);
        });

        assertThat(listOfMatchingConnections).as("List of connections")
                .isNotNull()
                .isEmpty();
    }



    @Test
    public void findConnectionByToCellHavingMultipleConnections(){
        Connection connection1 = generateConnection();
        Connection connection2 = generateConnection();

        connection2.setToCell(new CellInstance(connection2.getToCell().getCellType()));

        connectionDao.save(connection1);
        connectionDao.save(connection2);

        Iterable<Connection> connectionsResult = connectionDao.getConnectionsByToCell(connection1.getToCell());

        List<Connection> listOfMatchingConnections = new ArrayList<>();


        connectionsResult.forEach(conn -> {
            Optional<Connection> optionalConnection = connectionDao.findById(connection1.getId(),3);
            optionalConnection.ifPresent(listOfMatchingConnections::add);
        });

        assertThat(listOfMatchingConnections).as("List of connections")
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .first().isEqualTo(connection1);
    }

    @Test
    public void deleteByToCellAndVariable(){
        Connection connection1 = generateConnection();
        Connection connection2 = generateConnection();

        Connection connectionWithDifferentToCell = generateConnection();
        Connection connectionWithDifferentVariable = generateConnection();

        connection1 = connectionDao.save(connection1);
        connection2 = connectionDao.save(connection2);
        connectionWithDifferentToCell = connectionDao.save(connectionWithDifferentToCell);
        connectionWithDifferentVariable = connectionDao.save(connectionWithDifferentVariable);

    }

    @Test
    public void deleteByToCell() {
        Connection connection = generateConnection();

        connection = connectionDao.save(connection);

        connectionDao.deleteConnectionByToCellOrFromCell(connection.getToCell());

        boolean exists = connectionDao.existsById(connection.getId());

        assertThat(exists)
                .isFalse();
    }


    @Test
    public void deleteByFromCell() throws JsonProcessingException {
        Connection connection = generateConnection();

        connection = connectionDao.save(connection);

        connectionDao.deleteConnectionByToCellOrFromCell(connection.getFromCell());

        boolean exists = connectionDao.existsById(connection.getId());

        assertThat(exists)
            .isFalse();
    }
}
