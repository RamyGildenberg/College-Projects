package com.retinaX.services.simulation.simulateNetwork;

import com.retinaX.dal.ConnectionDao;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SimulationNetworkBuilderImplementation implements SimulationNetworkBuilder{

    private ConnectionDao connectionDao;

    @Override
    @Transactional(readOnly = true)
    public Map<CellInstance, Set<Connection>> getSimulationNetwork(List<CellInstance> simulationOutputCells) {
        return runBFSFrom(simulationOutputCells);

    }

    private Map<CellInstance, Set<Connection>> runBFSFrom(List<CellInstance> simulationOutputCells) {
        Queue<CellInstance> bfsQueue = new LinkedList<>(simulationOutputCells);
        Map<CellInstance, Set<Connection>> network = new HashMap<>();
        while(!bfsQueue.isEmpty()) {
            CellInstance cell = bfsQueue.poll();
            List<Connection> connections =  connectionDao.getConnectionsByToCell(cell);

            Iterable<Connection> connectionsWithDepth =

                connectionDao.findAllById(
                                connections.stream()
                                .map(Connection::getId)
                                .collect(Collectors.toList()), 4);

            connections.clear();
            connectionsWithDepth.forEach(connections::add);

            Set<Connection> connectionSet = new HashSet<>(connections);
            network.put(cell, connectionSet);
            List<CellInstance> cellsToAdd = getAllCellsFromConnectionsThatNotContainedInNetwork(network, bfsQueue, connectionSet);
            bfsQueue.addAll(cellsToAdd);
        }
        return network;
    }

    private List<CellInstance> getAllCellsFromConnectionsThatNotContainedInNetwork(Map<CellInstance, Set<Connection>> network, Queue<CellInstance> bfsQueue, Set<Connection> connections) {
        return connections.stream().map(Connection::getFromCell)
                .filter(Predicate.not(network::containsKey))
                .filter(Predicate.not(bfsQueue::contains))
                .collect(Collectors.toList());
    }

    private boolean isNumberOfConnectionsValid(CellInstance cell, Set<Connection> connections) {
        return connections.size() == cell.getCellType().getNumberOfInputs();
    }

    @Autowired
    public void setConnectionDao(ConnectionDao connectionDao) {
        this.connectionDao = connectionDao;
    }
}
