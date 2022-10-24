import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retinaX.Application;
import com.retinaX.coreAPI.buildNetworkAPI.requests.AddCellInstanceRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.ConnectCellsRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.CreateCellTypeRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.CreateFunctionRequest;
import com.retinaX.coreAPI.simulateNetworkAPI.SimulateNetworkAPI;
import com.retinaX.coreAPI.simulateNetworkAPI.requests.SimulateRequest;
import com.retinaX.coreAPI.simulateNetworkAPI.response.SimulationResult;
import com.retinaX.dal.ConnectionDao;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.services.buildNetwork.BuildNetworkService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimulateNetworkAPITest {

    @LocalServerPort
    private String port;

    private BuildNetworkService buildNetworkService;

    private SimulateNetworkAPI simulateNetworkAPI;

    private String url;
    private RestTemplate restTemplate;


    @PostConstruct
    public void init(){
        url = "http://localhost:" + port + "/simulate";
        System.err.println(url);
        restTemplate = new RestTemplate();
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testSimulationE2E() throws JsonProcessingException {
        final String VAR_A = "a", VAR_B = "b";

        CellType inputCellType = buildNetworkService.getCellTypes().get(0);

        CreateCellTypeRequest additionCellTypeRequest = new CreateCellTypeRequest(
                "Add",
                CellTransformType.ANALOG_TO_ANALOG,
                new CreateFunctionRequest(VAR_A + " + " + VAR_B , Set.of(VAR_A, VAR_B))
                );

        CellType additionCellType = buildNetworkService.createCellType(additionCellTypeRequest);

        CellInstance inputA = buildNetworkService.addCellInstance(new AddCellInstanceRequest(inputCellType.getId()));
        CellInstance inputB = buildNetworkService.addCellInstance(new AddCellInstanceRequest(inputCellType.getId()));

        CellInstance additionCell = buildNetworkService.addCellInstance(new AddCellInstanceRequest(additionCellType.getId()));

        ConnectCellsRequest connectAtoAddRequest = new ConnectCellsRequest(inputA, additionCell, VAR_A);
        ConnectCellsRequest connectBtoAddRequest = new ConnectCellsRequest(inputB, additionCell, VAR_B);
        buildNetworkService.connectCells(connectAtoAddRequest);
        buildNetworkService.connectCells(connectBtoAddRequest);


        SimulateRequest simulateRequest = new SimulateRequest(List.of(inputA, inputB, additionCell));

        simulateRequest.setMaxTime(3);

        Map<Long, List<Double>> simulationInput = new HashMap<>();

        simulationInput.put(inputA.getId(), List.of(1.0, 2.0, 3.0));
        simulationInput.put(inputB.getId(), List.of(7.0, 8.0, 9.0));
        simulateRequest.setUserInput(simulationInput);

        SimulationResult result = simulateNetworkAPI.simulate(simulateRequest);

        System.err.println("Simulation Results:\n" + new ObjectMapper().writeValueAsString(result) + " \n\n");

        result.getCellsResults().forEach((id, cellDataArr) ->{
            System.err.println("t | Cell ID: " + id);
            System.err.println("--------------------");
            for (int i = 0; i < cellDataArr.length; i++) {
                System.err.println(i + " | " +cellDataArr[i]);
            }
            System.err.println();
        });
    }

    @Autowired
    public void setBuildNetworkService(BuildNetworkService buildNetworkService){
        this.buildNetworkService = buildNetworkService;
    }

    @Autowired
    public void setSimulateNetworkAPI(SimulateNetworkAPI simulateNetworkAPI) {
        this.simulateNetworkAPI = simulateNetworkAPI;
    }
}
