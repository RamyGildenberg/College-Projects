package com.retinaX.coreAPI.buildNetworkAPI;

import com.retinaX.coreAPI.buildNetworkAPI.requests.*;
import com.retinaX.entities.*;
import com.retinaX.services.buildNetwork.BuildNetworkService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/build")

public class BuildNetworkAPIImplementation implements BuildNetworkAPI{

    private final BuildNetworkService buildNetworkService;

    @Autowired
    public BuildNetworkAPIImplementation(BuildNetworkService buildNetworkService) {
        this.buildNetworkService = buildNetworkService;
    }

    @Override
    @RequestMapping(
            path="/createCellType",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CellType createCellType(@RequestBody CreateCellTypeRequest createCellTypeRequest){
        return buildNetworkService.createCellType(createCellTypeRequest);
    }

    @Override
    @RequestMapping(
            path="/createSubGraph",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SubGraphInstance createSubGraph(@RequestBody AddSubGraphRequest addSubGraphRequest){
        SubGraphInstance subGraph = buildNetworkService.createSubGraph(addSubGraphRequest);
        return subGraph;

    }


    @Override
    @RequestMapping(
            path="/addCell",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CellInstance addCellInstance(@RequestBody AddCellInstanceRequest addCellInstanceRequest){
        return buildNetworkService.addCellInstance(addCellInstanceRequest);
    }

    @Override
    @RequestMapping(
            path="/connect",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CellInstance connectCells(@RequestBody ConnectCellsRequest connectCellsRequest) {
        return buildNetworkService.connectCells(connectCellsRequest);
    }

    @Override
    @RequestMapping(
            path="/areCompatible",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Boolean> areCompatible(@RequestParam(name="srcTransform") CellTransformType srcTransformType,
                                              @RequestParam(name="destTransform") CellTransformType destTransformType) {
        return Collections.singletonMap("value", buildNetworkService.areCompatible(srcTransformType, destTransformType));
    }

    @Override
    @RequestMapping(
            path="/cellTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CellType getCellType(@PathVariable(name="id") Long id) {
        return buildNetworkService.getCellType(id);
    }

    @Override
    @RequestMapping(
            path="/cellTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<CellType> getCellTypes() {
        return buildNetworkService.getCellTypes();
        //return new ArrayList<CellType>();
    }

    @Override
    @RequestMapping(
            path="/cellInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CellInstance getCellInstance(@PathVariable(name="id") Long id) {
        return buildNetworkService.getCellInstance(id);
    }

    @Override
    @RequestMapping(
            path="/cellInstances/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<CellInstance> getCellInstances() {
        return buildNetworkService.getCellInstances();
    }


    @Override
    @RequestMapping(
            path = "/inputCells/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<CellInstance> getInputCells() {
        return buildNetworkService.getInputCells();
    }

    @Override
    @RequestMapping(
            path="/connections/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Connection> getAllConnections() {
        return buildNetworkService.getConnections();
    }

    @GetMapping (
            path="/getSubGraphByName/{name}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SubGraphInstance> getSubGraph(@PathVariable("name") String id) {
        return buildNetworkService.getSubGraph(id);

    }

        @Override
        @RequestMapping(
                path="/cloneSubGraph/{id}",
                method = RequestMethod.POST,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE
        )
        public SubGraphInstance cloneSubGraph(@PathVariable(name="id") Long id) {

           return buildNetworkService.cloneSubGraph(id);
        }

    @Override
    @RequestMapping(
            path="/updateCellLocation/{x}/{y}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateCellCoordinates(@RequestBody CellInstance cellInstance, @PathVariable("x") double x, @PathVariable("y") double y) {
        buildNetworkService.updateCellInstanceCoordinates(cellInstance, x, y);
    }

    @Override
    @RequestMapping(path = "/cellTypes/{id}", method = RequestMethod.DELETE)
    public void deleteCellType(@PathVariable("id") Long id) {
        buildNetworkService.deleteCellType(id);
    }

    @Override
    @RequestMapping(path = "/cellInstances/{id}", method = RequestMethod.DELETE)
    public void deleteCellInstance(@PathVariable("id") Long id) {
        buildNetworkService.deleteCellInstance(id);
    }

    @Override
    @RequestMapping(path = "/connections/{id}", method = RequestMethod.DELETE)
    public void deleteConnection(@PathVariable("id") Long id) {
        buildNetworkService.deleteConnection(id);
    }
}
