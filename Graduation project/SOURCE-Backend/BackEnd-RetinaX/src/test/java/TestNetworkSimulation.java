
import com.retinaX.Application;
import com.retinaX.coreAPI.simulateNetworkAPI.response.SimulationResult;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.entities.Connection;
import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Function;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.simulation.simulateNetwork.SimulateNetworkServiceImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNetworkSimulation {

    private SimulateNetworkServiceImplementation simulateNetworkService;

    @Autowired
    public void setSimulateNetworkService(SimulateNetworkServiceImplementation simulateNetworkService) {
        this.simulateNetworkService = simulateNetworkService;
    }

    @Test
    public void testSimpleNetwork() {
        Map<CellInstance, Set<Connection>> network = new HashMap<>();
        Map<CellInstance, List<CellData>> inputData = new HashMap<>();
        int maxTime = 3;
        long id = 0L;

        Variable variables[] = {new Variable("x"), new Variable("a"), new Variable("b")};

        for (Variable var : variables) {
            var.setId(id++);
        }

        Function inputCellFunction = new Function("x", Set.of(variables[0]));
        inputCellFunction.setId(id++);
        CellType inputCellType = new CellType("Input Cell", CellTransformType.INPUT_TO_ANALOG, inputCellFunction);
        inputCellType.setId(id++);


        Function addFunction = new Function("a + b", Set.of(variables[1], variables[2]));
        addFunction.setId(id++);
        CellType addCellType = new CellType("Add Cell", CellTransformType.ANALOG_TO_ANALOG, addFunction);
        addCellType.setId(id++);

        CellInstance firstInput = new CellInstance(inputCellType);
        firstInput.setId(id++);
        CellInstance secondInput = new CellInstance(inputCellType);
        secondInput.setId(id++);
        CellInstance addCell = new CellInstance(addCellType);
        addCell.setId(id++);

        Connection firstToAdd = new Connection(0, firstInput, addCell, variables[1]);
        Connection secondToAdd = new Connection(0, secondInput, addCell, variables[2]);

        firstToAdd.setId(id++);
        secondToAdd.setId(id);


        network.put(firstInput, Set.of());
        network.put(secondInput, Set.of());
        network.put(addCell, Set.of(firstToAdd, secondToAdd));

        inputData.put(firstInput, List.of(new CellData(1), new CellData(2), new CellData(3)));
        inputData.put(secondInput, List.of(new CellData(2), new CellData(3), new CellData(4)));

        List<CellInstance> outputCells = new ArrayList<>(List.of(firstInput, secondInput, addCell));
        SimulationResult simulationResult = simulateNetworkService.simulateNetwork(network, inputData, outputCells, maxTime);

        simulationResult.getCellsResults().forEach((cellId, results) ->{
            System.err.println("\nCell #"+cellId+":");
            int t = 0;
            for(CellData cellData : results){
                System.err.println(t++ + ": " +cellData.getValue());
            }
        });

        Map<Long, List<Double>> simplifiedResults = new HashMap<>();
        simulationResult.getCellsResults().forEach((outputID, cellData) ->
                simplifiedResults.put(outputID, Stream.of(cellData).map(CellData::getValue).collect(Collectors.toList())));

        Map<Long, List<Double>> expectedResults = new HashMap<>();

        expectedResults.put(firstInput.getId(),
                inputData.get(firstInput).stream().map(CellData::getValue).collect(Collectors.toList()));

        expectedResults.put(secondInput.getId(),
                inputData.get(secondInput).stream().map(CellData::getValue).collect(Collectors.toList()));

        expectedResults.put(addCell.getId(),
               List.of(3.0, 5.0, 7.0));

        assertThat(simplifiedResults)
            .containsAllEntriesOf(expectedResults);

    }


    @Test
    public void testNetworkWithDelay(){
        testSimpleNetworkWithDelay(3, 1, 0);
    }

    public void testSimpleNetworkWithDelay(int maxTime, int firstInputDelay, int secondInputDelay) {
        Map<CellInstance, Set<Connection>> network = new HashMap<>();
        Map<CellInstance, List<CellData>> inputData = new HashMap<>();

        long id = 0L;

        Variable variables[] = {new Variable("x"), new Variable("a"), new Variable("b")};

        for (Variable var : variables) {
            var.setId(id++);
        }

        Function inputCellFunction = new Function("x", Set.of(variables[0]));
        inputCellFunction.setId(id++);
        CellType inputCellType = new CellType("Input Cell", CellTransformType.INPUT_TO_ANALOG, inputCellFunction);
        inputCellType.setId(id++);


        Function addFunction = new Function("a + b", Set.of(variables[1], variables[2]));
        addFunction.setId(id++);
        CellType addCellType = new CellType("Add Cell", CellTransformType.ANALOG_TO_ANALOG, addFunction);
        addCellType.setId(id++);

        CellInstance firstInput = new CellInstance(inputCellType);
        firstInput.setId(id++);
        CellInstance secondInput = new CellInstance(inputCellType);
        secondInput.setId(id++);
        CellInstance addCell = new CellInstance(addCellType);
        addCell.setId(id++);

        Connection firstToAdd = new Connection(firstInputDelay, firstInput, addCell, variables[1]);
        Connection secondToAdd = new Connection(secondInputDelay, secondInput, addCell, variables[2]);

        firstToAdd.setId(id++);
        secondToAdd.setId(id);


        network.put(firstInput, Set.of());
        network.put(secondInput, Set.of());
        network.put(addCell, Set.of(firstToAdd, secondToAdd));

        inputData.put(firstInput, List.of(new CellData(1), new CellData(2), new CellData(3)));
        inputData.put(secondInput, List.of(new CellData(2), new CellData(3), new CellData(4)));

        List<CellInstance> outputCells = new ArrayList<>(List.of(firstInput, secondInput, addCell));
        SimulationResult simulationResult = simulateNetworkService.simulateNetwork(network, inputData, outputCells, maxTime);

        simulationResult.getCellsResults().forEach((cellId, results) ->{
            System.err.println("\nt| Cell #"+cellId+":");
            System.err.println("---------------------");
            int t = 0;
            for(CellData cellData : results){
                System.err.println(t++ + "| " +cellData.getValue());
            }
        });
//
//        Map<Long, List<Double>> simplifiedResults = new HashMap<>();
//        simulationResult.getCellsResults().forEach((outputID, cellData) ->
//                simplifiedResults.put(outputID, Stream.of(cellData).map(CellData::getValue).collect(Collectors.toList())));
//
//        Map<Long, List<Double>> expectedResults = new HashMap<>();
//
//        expectedResults.put(firstInput.getId(),
//                inputData.get(firstInput).stream().map(CellData::getValue).collect(Collectors.toList()));
//
//        expectedResults.put(secondInput.getId(),
//                inputData.get(secondInput).stream().map(CellData::getValue).collect(Collectors.toList()));
//
//        expectedResults.put(addCell.getId(),
//                List.of(3.0, 5.0, 7.0));
//
//        assertThat(simplifiedResults)
//                .containsAllEntriesOf(expectedResults);

    }

}
