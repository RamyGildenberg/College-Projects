package com.retinaX.coreAPI.simulateNetworkAPI;

import com.retinaX.coreAPI.simulateNetworkAPI.requests.SimulateRequest;
import com.retinaX.coreAPI.simulateNetworkAPI.response.SimulationResult;
import com.retinaX.services.simulation.simulateNetwork.SimulateNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/simulate")
public class SimulateNetworkAPIImplementation implements SimulateNetworkAPI {

    private SimulateNetworkService simulateNetworkService;

    @Autowired
    public SimulateNetworkAPIImplementation(SimulateNetworkService simulateNetworkService){
        setSimulateNetworkService(simulateNetworkService);
    }

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SimulationResult simulate(@RequestBody SimulateRequest simulateRequest) {
        return simulateNetworkService.simulateNetwork(simulateRequest);
    }


    private void setSimulateNetworkService(SimulateNetworkService simulateNetworkService) {
        this.simulateNetworkService = simulateNetworkService;
    }

    @Override
    @RequestMapping(
            path="/isNetworkValid/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Boolean> isNetworkStructureValid() {
        return new HashMap<>(Map.of("value", simulateNetworkService.isNetworkStructureValid()));
    }
}
