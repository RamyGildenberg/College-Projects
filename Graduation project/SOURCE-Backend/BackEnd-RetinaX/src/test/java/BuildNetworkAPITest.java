import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retinaX.Application;
import com.retinaX.coreAPI.buildNetworkAPI.requests.AddCellInstanceRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.ConnectCellsRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.CreateCellTypeRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.CreateFunctionRequest;
import com.retinaX.dal.ConnectionDao;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellType;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.Connection;
import com.retinaX.entities.function.Variable;
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


import java.util.*;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildNetworkAPITest {

    @LocalServerPort
    private String port;

    private BuildNetworkService buildNetworkService;

    private ConnectionDao connectionDao;

    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public void setBuildNetworkService(BuildNetworkService buildNetworkService){
        this.buildNetworkService = buildNetworkService;
    }

    @Autowired
    public void setConnectionDao(ConnectionDao connectionDao) {
        this.connectionDao = connectionDao;
    }

    @PostConstruct
    public void init(){
        url = "http://localhost:" + port + "/build";
        System.err.println(url);
        restTemplate = new RestTemplate();
    }



    @Before
    public void beforeEachTest(){

    }

    @After
    public void afterEachTest(){
      // buildNetworkService.clear();
    }



    private CreateCellTypeRequest mockCellTypeRequest(){
        String cellTypeName = "A-Cell";
        CellTransformType transformType = CellTransformType.ANALOG_TO_ANALOG;
        String cellFunctionExpression = "a + b * c";
        Set<String> setOfFunctionVariables = Set.of("a", "b", "c");
        CreateFunctionRequest cellFunctionRequest = new CreateFunctionRequest(cellFunctionExpression, setOfFunctionVariables);
        return new CreateCellTypeRequest(cellTypeName, transformType, cellFunctionRequest);
    }

    @Test
    public void mockANetwork(){
        final int N = 3;
        CreateCellTypeRequest cellTypeRequest = mockCellTypeRequest();
        CreateCellTypeRequest cellTypeRequest2 = mockCellTypeRequest();
        cellTypeRequest2.setName("B-Cell");
        CellType[] cellTypes = new CellType[]{buildNetworkService.createCellType(cellTypeRequest),
                buildNetworkService.createCellType(cellTypeRequest2)};

        ArrayList<CellInstance> cellInstances = new ArrayList<>();

        for(int i = 0 ; i < N ; i++){
            AddCellInstanceRequest addCellInstanceRequest = new AddCellInstanceRequest(cellTypes[i%cellTypes.length].getId());
            CellInstance cellInstance = buildNetworkService.addCellInstance(addCellInstanceRequest);
            cellInstances.add(cellInstance);
        }

        Random random = new Random();
        for(int i = 0 ; i < N - 1 ; i++){

            CellInstance source = cellInstances.get(random.nextInt(N));
            CellInstance destination = cellInstances.get(random.nextInt(N));

            if(!source.getId().equals(destination.getId())) {
                Variable variable = destination.getCellType().getFunction().getVariables().stream().skip(i % 3).findAny().get();
                ConnectCellsRequest connectCellsRequest = new ConnectCellsRequest(source, destination, variable.getVariableName(), 4);
                buildNetworkService.connectCells(connectCellsRequest);
            }
        }
    }

    @Test
    public void createCellTypeSuccessfullyNameAndTransformTypeTest(){

        CreateCellTypeRequest createCellTypeRequest = mockCellTypeRequest();
        CellType cellType = restTemplate.postForObject(url + "/createCellType/", createCellTypeRequest, CellType.class);

        assertThat(cellType)
                .isNotNull()
                .extracting("name", "transformType")
                .containsExactly(createCellTypeRequest.getName(), createCellTypeRequest.getTransformType());
    }

    @Test
    public void createCellTypeSuccessfullyFunctionTest(){
        CreateCellTypeRequest createCellTypeRequest = mockCellTypeRequest();

        CellType cellType = restTemplate.postForObject(url + "/createCellType/", createCellTypeRequest, CellType.class);

        assertThat(cellType)
                .isNotNull();

        assertThat(cellType.getFunction())
                .isNotNull()
                .extracting("expression")
                .containsExactly(
                        createCellTypeRequest
                        .getCreateFunctionRequest()
                        .getExpression()
                );

        assertThat(cellType.getFunction().getVariables().stream().map(Variable::getVariableName)
                .toArray())
                .containsExactlyInAnyOrder(createCellTypeRequest.getCreateFunctionRequest()
                .getVariables().toArray());

    }

    @Test(expected = Exception.class)
    public void createCellTypeWithNullRequestAttributes(){
        CreateCellTypeRequest createCellTypeRequest = new CreateCellTypeRequest();
        restTemplate.postForObject(url + "/createCellType/", createCellTypeRequest, CellType.class);
    }

    @Test
    public void addCellInstanceSuccessfully(){
        String cellTypeName = "B-Cell";
        CreateCellTypeRequest cellTypeRequest = new CreateCellTypeRequest(cellTypeName, CellTransformType.ANALOG_TO_ANALOG,
                new CreateFunctionRequest("a =  b + c + d", Set.of("a", "b", "c", "d")));

        CellType cellType = buildNetworkService.createCellType(cellTypeRequest);

        AddCellInstanceRequest addCellInstanceRequest = new AddCellInstanceRequest(cellType.getId());
        CellInstance cellInstance = restTemplate.postForObject(url + "/addCell/", addCellInstanceRequest, CellInstance.class);
/*
        System.err.println("$$$ Cell In$tance $tring: " + cellInstanceString);


        ObjectMapper objectMapper = new ObjectMapper();
        CellInstance cellInstance = null;
        try {
            cellInstance = objectMapper.readValue(cellInstanceString, CellInstance.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //restTemplate.postForObject(url + "/addCell/", addCellInstanceRequest, CellInstance.class);
*/
        assertThat(cellInstance)
                .isNotNull()
                .extracting("id")
                .isNotNull()
                .isNotEmpty();

        assertThat(cellInstance.getCellType())
                .isNotNull()
                .extracting("name", "id", "transformType")
                .containsExactlyInAnyOrder(cellType.getName(), cellType.getId(), cellType.getTransformType());



        assertThat(cellInstance.getCellType().getFunction())
                .isNotNull()
                .extracting("id", "expression")
                .containsExactlyInAnyOrder(cellType.getFunction().getId(),
                        cellType.getFunction().getExpression());

        assertThat(cellInstance.getCellType().getFunction().getVariables())
                .containsExactlyInAnyOrder(cellType.getFunction().getVariables().toArray(new Variable[0]));

    }

    @Test(expected = Exception.class)
    public void addCellInstanceWithNonExistTypeTest(){
        Long cellTypeId = -12345L;

        AddCellInstanceRequest addCellInstanceRequest = new AddCellInstanceRequest(cellTypeId);

        restTemplate.postForObject(url + "/addCell/", addCellInstanceRequest, CellInstance.class);
    }

    private ConnectCellsRequest mockConnectCellsRequest(){
        CreateCellTypeRequest createCellTypeRequestA = new CreateCellTypeRequest("A-Cell-Type", CellTransformType.DIGITAL_TO_ANALOG,
                new CreateFunctionRequest("x + y + z", new HashSet<>(List.of("x", "y", "z"))));
        CellType aCellType  = buildNetworkService.createCellType(createCellTypeRequestA);

        CreateCellTypeRequest createCellTypeRequestB = new CreateCellTypeRequest("B-Cell-Type", CellTransformType.ANALOG_TO_DIGITAL,
                new CreateFunctionRequest("a + b + c", new HashSet<>(List.of("a", "b", "c"))));

        CellType bCellType = buildNetworkService.createCellType(createCellTypeRequestB);


        String inputFunctionVariable = "b";

        AddCellInstanceRequest addCellInstanceRequestA = new AddCellInstanceRequest(aCellType.getId());
        AddCellInstanceRequest addCellInstanceRequestB = new AddCellInstanceRequest(bCellType.getId());
        CellInstance sourceCell = buildNetworkService.addCellInstance(addCellInstanceRequestA);
        CellInstance destinationCell = buildNetworkService.addCellInstance(addCellInstanceRequestB);

        return new ConnectCellsRequest(sourceCell, destinationCell, inputFunctionVariable);
    }

    private ConnectCellsRequest mockConnectCellsOfTheSameTypeRequest(){
        CreateCellTypeRequest createCellTypeRequestA = new CreateCellTypeRequest("C-Cell-Type", CellTransformType.ANALOG_TO_ANALOG,
                new CreateFunctionRequest("o && g || r && d", new HashSet<>(List.of("o", "g", "r", "d"))));
        CellType aCellType  = buildNetworkService.createCellType(createCellTypeRequestA);

        String inputFunctionVariable = "r";

        AddCellInstanceRequest addCellInstanceRequestA = new AddCellInstanceRequest(aCellType.getId());
        AddCellInstanceRequest addCellInstanceRequestB = new AddCellInstanceRequest(aCellType.getId());
        CellInstance sourceCell = buildNetworkService.addCellInstance(addCellInstanceRequestA);
        CellInstance destinationCell = buildNetworkService.addCellInstance(addCellInstanceRequestB);

        return new ConnectCellsRequest(sourceCell, destinationCell, inputFunctionVariable);
    }





    @Test
    public void connectCellsSuccessfullyCheckId(){
        ConnectCellsRequest connectCellsRequest = mockConnectCellsRequest();
        CellInstance resultCellInstance = restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);

        assertThat(resultCellInstance)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", connectCellsRequest.getDestinationCell().getId());
    }

    @Test
    public void connectCellsSuccessfullyCheckInputCells(){
        ConnectCellsRequest connectCellsRequest = mockConnectCellsRequest();
        CellInstance resultCellInstance = restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);

        String inputFunctionVariable = connectCellsRequest.getInputFunctionVariable();
//
//        assertThat(resultCellInstance)
//                .isNotNull()
//                .matches(cellInstance ->
//                        cellInstance.getInputConnections()
//                                    .stream()
//                                            .anyMatch(connection ->
//                                                    connection.getVariable().getVariableName()
//                                                            .equals(inputFunctionVariable)
//
//                                            && connection.getFromCell()
//                                                            .equals(connectCellsRequest.getSourceCell())),

//                        "to match " + connectCellsRequest.getSourceCell());
    }


    @Test
    public void connectCellsOfTheSameTypeSuccessfullyCheckId(){
        ConnectCellsRequest connectCellsRequest = mockConnectCellsOfTheSameTypeRequest();
        CellInstance resultCellInstance = restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);

        assertThat(resultCellInstance)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", connectCellsRequest.getDestinationCell().getId());
    }

    @Test
    public void connectCellsWhenDestinationVariableAlreadyHasInputConnection(){
        ConnectCellsRequest connectCellsRequest = mockConnectCellsRequest();
        CellInstance destination = buildNetworkService.connectCells(connectCellsRequest);

        CreateCellTypeRequest cellTypeRequest = mockCellTypeRequest();
        CellType cellType = buildNetworkService.createCellType(cellTypeRequest);
        AddCellInstanceRequest addCellInstanceRequest = new AddCellInstanceRequest(cellType.getId());
        CellInstance anotherSourceInstance = buildNetworkService.addCellInstance(addCellInstanceRequest);

        ConnectCellsRequest connectCellsRequest2 = new ConnectCellsRequest(anotherSourceInstance, destination, connectCellsRequest.getInputFunctionVariable());
        CellInstance sameDestinationCell = buildNetworkService.connectCells(connectCellsRequest2);


        List<Connection> connections = new ArrayList<>();
                connectionDao.findAll(3).forEach(connection -> {
                    if(connection.getToCell().getId().equals(sameDestinationCell.getId()))
                        connections.add(connection);
                });


        List<Connection> connectionsToVariable = connections.stream().filter(connection ->
                connection.getVariable().getVariableName().equals(connectCellsRequest.getInputFunctionVariable())).collect(Collectors.toList());

        assertThat(connectionsToVariable)
                .isNotNull()
                .hasSize(1).first().hasFieldOrPropertyWithValue("fromCell", anotherSourceInstance);
    }


    @Test (expected = Exception.class)
    public void connectCellsWhileSourceCellDoesNotExist(){
        Long nonExistingId = -12345L;
        ConnectCellsRequest connectCellsRequest = mockConnectCellsRequest();
        connectCellsRequest.getSourceCell().setId(nonExistingId);
        restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);
    }

    @Test (expected = Exception.class)
    public void connectCellsWhileDestinationCellDoesNotExist(){
        Long nonExistingId = -12345L;
        ConnectCellsRequest connectCellsRequest = mockConnectCellsRequest();
        connectCellsRequest.getDestinationCell().setId(nonExistingId);
        restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);
    }

    @Test (expected = Exception.class)
    public void connectCellsWithNullValuesRequest(){
        restTemplate.postForObject(url + "/connect", new ConnectCellsRequest(), CellInstance.class);
    }

    @Test (expected = Exception.class)
    public void connectCellsWithNonMatchingTransformTypes(){
        CreateCellTypeRequest createCellTypeRequestA = new CreateCellTypeRequest("A-Cell-Type", CellTransformType.DIGITAL_TO_DIGITAL,
                new CreateFunctionRequest("x + y + z", new HashSet<>(List.of("x", "y", "z"))));
        CellType aCellType  = buildNetworkService.createCellType(createCellTypeRequestA);

        CreateCellTypeRequest createCellTypeRequestB = new CreateCellTypeRequest("B-Cell-Type", CellTransformType.ANALOG_TO_ANALOG,
                new CreateFunctionRequest("a + b + c", new HashSet<>(List.of("a", "b", "c"))));

        CellType bCellType = buildNetworkService.createCellType(createCellTypeRequestB);

        String inputFunctionVariable = "b";

        AddCellInstanceRequest addCellInstanceRequestA = new AddCellInstanceRequest(aCellType.getId());
        AddCellInstanceRequest addCellInstanceRequestB = new AddCellInstanceRequest(bCellType.getId());
        CellInstance sourceCell = buildNetworkService.addCellInstance(addCellInstanceRequestA);
        CellInstance destinationCell = buildNetworkService.addCellInstance(addCellInstanceRequestB);

        ConnectCellsRequest connectCellsRequest = new ConnectCellsRequest(sourceCell, destinationCell, inputFunctionVariable);
        restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);
    }

    @Test (expected = Exception.class)
    public void connectCellsWithNonExistingFunctionInputVariable(){
        ConnectCellsRequest connectCellsRequest = mockConnectCellsRequest();
        connectCellsRequest.setInputFunctionVariable("Non-Existing-Input-Function-Variable");
        restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);
    }

    @Test
    public void connectCellsFullFlowUsingRestSuccessfully(){
        CreateCellTypeRequest createCellTypeRequestA = new CreateCellTypeRequest("A-Cell-Type", CellTransformType.DIGITAL_TO_ANALOG,
                new CreateFunctionRequest("x + y + z", new HashSet<>(List.of("x", "y", "z"))));
        CellType aCellType = restTemplate.postForObject(url + "/createCellType/", createCellTypeRequestA, CellType.class);

        CreateCellTypeRequest createCellTypeRequestB = new CreateCellTypeRequest("B-Cell-Type", CellTransformType.ANALOG_TO_DIGITAL,
                new CreateFunctionRequest("a + b + c", new HashSet<>(List.of("a", "b", "c"))));

        CellType bCellType = restTemplate.postForObject(url + "/createCellType/", createCellTypeRequestB, CellType.class);

        String inputFunctionVariable = "b";

        AddCellInstanceRequest addCellInstanceRequestA = new AddCellInstanceRequest(aCellType.getId());
        AddCellInstanceRequest addCellInstanceRequestB = new AddCellInstanceRequest(bCellType.getId());

        CellInstance aCellInstance = restTemplate.postForObject(url + "/addCell/", addCellInstanceRequestA, CellInstance.class);

        CellInstance bCellInstance = restTemplate.postForObject(url + "/addCell/", addCellInstanceRequestB, CellInstance.class);

        ConnectCellsRequest connectCellsRequest = new ConnectCellsRequest(aCellInstance, bCellInstance, inputFunctionVariable);
        CellInstance resultCellInstance = restTemplate.postForObject(url + "/connect", connectCellsRequest, CellInstance.class);

        assertThat(resultCellInstance)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", bCellInstance.getId());

//        Optional<Connection> connectionOptional = resultCellInstance.getInputConnections().stream()
//                .filter(connection -> connection.getVariable().getVariableName().equals(inputFunctionVariable)).findAny();
//
//        assertThat(connectionOptional.isPresent())
//                .isNotNull()
//                .isEqualTo(true);
//        if(connectionOptional.isPresent()){
//            Connection connection = connectionOptional.get();
//            assertThat(resultCellInstance.getInputConnections())
//                    .isNotNull().contains(connection);
//        }
    }

    @Test
    public void getCreatedCellType(){
        CreateCellTypeRequest createCellTypeRequest = mockCellTypeRequest();
        CellType cellType = buildNetworkService.createCellType(createCellTypeRequest);
        CellType resultCellType = restTemplate.getForObject(url + "/cellTypes/{id}", CellType.class, cellType.getId());

        assertThat(resultCellType)
                .isNotNull()
                .isEqualToComparingOnlyGivenFields(cellType, "id");
    }

    @Test(expected = Exception.class)
    public void getNonExistingCellType(){
        restTemplate.getForObject(url + "/cellTypes/{id}", CellType.class, "NonExistingCellTypeId");
    }

    @Test
    public void getExistingCellInstance(){
        CreateCellTypeRequest createCellTypeRequest = mockCellTypeRequest();
        CellType cellType = buildNetworkService.createCellType(createCellTypeRequest);
        AddCellInstanceRequest addCellInstanceRequest = new AddCellInstanceRequest(cellType.getId());
        CellInstance cellInstance = buildNetworkService.addCellInstance(addCellInstanceRequest);

        CellInstance returnedInstance = restTemplate.getForObject(url + "/cellInstances/{id}", CellInstance.class, cellInstance.getId());

        assertThat(returnedInstance)
                .isNotNull()
                .isEqualToIgnoringGivenFields(cellInstance);
    }

    @Test(expected = Exception.class)
    public void getNonExistingCellInstance(){
        restTemplate.getForObject(url + "/cellInstances/{id}", CellInstance.class, "NonExistingCellInstanceId");
    }


    @Test
    public void getAllCellTypesWhenThereAreNoCellTypes(){
        CellType[] list =  restTemplate.getForObject(url + "/cellTypes/", CellType[].class);
        assertThat(list)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void getAllExistingCellTypes(){
        CreateCellTypeRequest createCellTypeRequest = mockCellTypeRequest();
        ConnectCellsRequest connectCellsRequest = mockConnectCellsRequest();

        CellType cellTypeA = buildNetworkService.createCellType(createCellTypeRequest);
        CellType cellTypeB = connectCellsRequest.getSourceCell().getCellType();
        CellType cellTypeC = connectCellsRequest.getDestinationCell().getCellType();

        CellType[] cellTypesResponse = restTemplate.getForObject(url + "/cellTypes/", CellType[].class);


        CellType[] expected = {cellTypeA, cellTypeB, cellTypeC};

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String responseJSON = objectMapper.writeValueAsString(cellTypesResponse);
            String expectedJSON = objectMapper.writeValueAsString(cellTypesResponse);
            assertThat(responseJSON).isEqualTo(expectedJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }



}
