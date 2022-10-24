package com.retinaX.services.simulation.simulateNetwork;

import com.retinaX.coreAPI.simulateNetworkAPI.requests.SimulateRequest;
import com.retinaX.coreAPI.simulateNetworkAPI.response.SimulationResult;

public interface SimulateNetworkService {

    SimulationResult simulateNetwork(SimulateRequest simulateRequest);

    Boolean isNetworkStructureValid();
}
