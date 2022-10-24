package com.retinaX.coreAPI.simulateNetworkAPI;

import com.retinaX.coreAPI.simulateNetworkAPI.requests.SimulateRequest;
import com.retinaX.coreAPI.simulateNetworkAPI.response.SimulationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface SimulateNetworkAPI {

    SimulationResult simulate(SimulateRequest simulateRequest);

    @RequestMapping(
            path="/isNetworkValid/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Map<String, Boolean> isNetworkStructureValid();
}
