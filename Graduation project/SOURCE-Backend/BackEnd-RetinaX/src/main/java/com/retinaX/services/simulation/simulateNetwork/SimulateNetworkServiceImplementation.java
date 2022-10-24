package com.retinaX.services.simulation.simulateNetwork;

import com.retinaX.coreAPI.simulateNetworkAPI.requests.SimulateRequest;
import com.retinaX.coreAPI.simulateNetworkAPI.response.SimulationResult;
import com.retinaX.dal.CellInstanceDao;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.Connection;
import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.simulation.cellSimulation.CellInstanceController;
import com.retinaX.services.simulation.cellSimulation.CellInstanceControllerFactory;
import com.retinaX.services.simulation.cellSimulation.CellSubscriber;
import com.retinaX.services.simulation.cellSimulation.SimulationResultCellSubscriber;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources.CellDataSource;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources.ConnectionsDataSource;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources.InputCellDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class SimulateNetworkServiceImplementation implements SimulateNetworkService {

    private CellInstanceDao cellInstanceDao;

    private SimulationNetworkBuilder simulationNetworkBuilder;

    private CellInstanceControllerFactory cellInstanceControllerFactory;

    private ExecutorService executorService;

    public SimulateNetworkServiceImplementation() {
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public SimulationResult simulateNetwork(SimulateRequest simulateRequest) {
        List<CellInstance> outputCells = getOutputCells(simulateRequest);
        Map<CellInstance, Set<Connection>> network = simulationNetworkBuilder.getSimulationNetwork(outputCells);
        SimulationResult simulationResult;
        List<CellInstance> illegalConnectivityCellInstances = findCellInstancesWithIllegalConnectivity(network);
        if(!illegalConnectivityCellInstances.isEmpty()){
            simulationResult = new SimulationResult(illegalConnectivityCellInstances, false);
        }else{
            Map<CellInstance, List<CellData>> userInputMap = resolveUserInput(simulateRequest.getUserInput(), network);

            simulationResult = simulateNetwork(network, userInputMap, outputCells, simulateRequest.getMaxTime());
        }
        return simulationResult;
    }

    private Map<CellInstance, List<CellData>> resolveUserInput(Map<Long, List<Double>> userMap, Map<CellInstance, Set<Connection>> network){
        Map<CellInstance, List<CellData>> userInputData = new HashMap<>();

        userMap.forEach((id, dataList) -> {
            Optional<CellInstance> optionalCellInstance = network.
                                                    keySet().stream().
                                                    filter(instance -> instance.getId().equals(id))
                                                    .findAny();

            optionalCellInstance.ifPresent(cellInstance ->
                                userInputData.put(cellInstance, dataList.stream()
                                        .map(CellData::new)
                                        .collect(Collectors.toList())));
        });
        return userInputData;
    }

    public SimulationResult simulateNetwork(Map<CellInstance, Set<Connection>> network,
                                             Map<CellInstance, List<CellData>> inputData,
                                             List<CellInstance> outputCells,
                                             int maxTime) {

        executorService = Executors.newCachedThreadPool();
        Map<CellInstance, CellInstanceController> cellControllers = new HashMap<>();

        network.forEach((instance, connections) -> {
            CellInstanceController cellController = cellInstanceControllerFactory.makeCellInstanceController(instance);
            cellControllers.put(instance, cellController);
        });

        Map<CellInstance, SimulationResultCellSubscriber> outputSubscribers = new HashMap<>();

        outputCells.forEach(outputCell -> {
            SimulationResultCellSubscriber cellSubscriber = new SimulationResultCellSubscriber(maxTime);
            outputSubscribers.put(outputCell, cellSubscriber);
            cellControllers.get(outputCell).subscribe(cellSubscriber);
        });

        cellControllers.forEach((instance, controller) -> {
            CellDataSource cellDataSource;

            if(instance.getCellType().getInputType().equals(CellTransformType.Type.INPUT)){
                cellDataSource = new InputCellDataSource(instance.getCellType().getFunction().getVariables().iterator().next()
                        , inputData.get(instance));
            }else{
               cellDataSource = getConnectionsCellDataSource(network.get(instance), cellControllers);
            }

            executorService.execute(() -> {
                //System.err.println(Thread.currentThread().getName() + ":: Starting cell simulation of: " + instance);
                controller.simulateCell(maxTime, cellDataSource);
            });
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(1000, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Map<Long, CellData[]> results = new HashMap<>();
        outputSubscribers.forEach((instance, outputSubscriber) ->{
            results.put(instance.getId(), outputSubscriber.getResults());
        });

        return new SimulationResult(results);
    }

    private ConnectionsDataSource getConnectionsCellDataSource(Set<Connection> connections, Map<CellInstance, CellInstanceController> cellControllers) {
        Map<Connection, CellInstanceController> inputConnections = new HashMap<>();

        connections.forEach((connection) -> {
            inputConnections.put(connection, cellControllers.get(connection.getFromCell()));
        });

        return new ConnectionsDataSource(inputConnections);
    }



    private List<CellInstance> findCellInstancesWithIllegalConnectivity(Map<CellInstance, Set<Connection>> network) {

        List<CellInstance> illegalCellInstances = new ArrayList<>();

        network.forEach((instance, connections) -> {
            if (!instance.getCellType().getInputType().equals(CellTransformType.Type.INPUT) &&
                    connections.size() != instance.getCellType().getNumberOfInputs()) {
                illegalCellInstances.add(instance);
            }
        });

        return illegalCellInstances;
    }


    @Override
    public Boolean isNetworkStructureValid() {
        List<CellInstance> cellInstances = new LinkedList<>();
        cellInstanceDao.findAll(3).forEach(cellInstances::add);
        Map<CellInstance, Set<Connection>> network = simulationNetworkBuilder.getSimulationNetwork(cellInstances);
        return findCellInstancesWithIllegalConnectivity(network).size() == 0;
    }

    @Transactional
    protected List<CellInstance> getOutputCells(SimulateRequest simulateRequest) {
        Iterable<CellInstance> outputCells = cellInstanceDao.findAllById(
                simulateRequest.getOutputCells()
                .stream().map(CellInstance::getId)
                .collect(Collectors.toList()),4
        );
        List<CellInstance> cellInstanceList = new ArrayList<>(simulateRequest.getOutputCells().size());
        outputCells.forEach(cellInstanceList::add);
        return cellInstanceList;
    }

    @Autowired
    public void setSimulationNetworkBuilder(SimulationNetworkBuilder simulationNetworkBuilder) {
        this.simulationNetworkBuilder = simulationNetworkBuilder;
    }

    @Autowired
    public void setCellInstanceDao(CellInstanceDao cellInstanceDao) {
        this.cellInstanceDao = cellInstanceDao;
    }

    @Autowired
    public void setCellInstanceControllerFactory(CellInstanceControllerFactory cellInstanceControllerFactory) {
        this.cellInstanceControllerFactory = cellInstanceControllerFactory;
    }
}
