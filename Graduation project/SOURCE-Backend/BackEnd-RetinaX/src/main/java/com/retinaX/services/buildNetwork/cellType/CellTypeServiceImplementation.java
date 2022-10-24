package com.retinaX.services.buildNetwork.cellType;

import com.retinaX.coreAPI.buildNetworkAPI.requests.CreateCellTypeRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.CreateFunctionRequest;
import com.retinaX.dal.CellTypeDao;
import com.retinaX.dal.FunctionDao;
import com.retinaX.dal.VariableDao;
import com.retinaX.dal.utils.IdGeneratorService;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.entities.function.Function;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.function.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CellTypeServiceImplementation implements CellTypeService{
    private CellTypeDao cellTypeDao;

    private FunctionDao functionDao;

    private VariableDao variableDao;

    private IdGeneratorService idGeneratorService;

    public CellTypeServiceImplementation(){

    }

    @PostConstruct
    private void init(){
//        Optional<CellType> a = cellTypeDao.findCellTypeByTransformType(CellTransformType.INPUT_TO_ANALOG);
//
//        if(!a.isPresent()) {
//
//            CreateCellTypeRequest createInputCellTypeRequest = new CreateCellTypeRequest("Input Cell", CellTransformType.INPUT_TO_ANALOG,
//                    new CreateFunctionRequest("x", Set.of("x")));
//            createCellType(createInputCellTypeRequest);
//        }
    }

    @Transactional
    @Override
    public CellType createCellType(CreateCellTypeRequest createCellTypeRequest) {
        CreateFunctionRequest createFunctionRequest = createCellTypeRequest.getCreateFunctionRequest();

        Set<Variable> variables = createFunctionRequest.getVariables().stream()
                .map(Variable::new)
                .collect(Collectors.toSet());

        Function function = new Function(createFunctionRequest.getExpression(),
                variables);

        CellType cellType = new CellType(createCellTypeRequest.getName(),
                createCellTypeRequest.getTransformType(), function);

        variableDao.saveAll(function.getVariables());
        functionDao.save(function);
        return cellTypeDao.save(cellType);
    }

    @Override
    public  boolean areCompatible(CellInstance source, CellInstance destination){
        return areCompatible(source.getCellType(), destination.getCellType());
    }

    @Override
    public boolean areCompatible(CellType source, CellType destination){
        return CellTransformType.areCompatible(source.getTransformType(), destination.getTransformType());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CellType> getExistingCellTypes() {
        List<CellType> cellTypesList = new ArrayList<>();
        cellTypeDao.findAll(3).forEach(cellTypesList::add);
        return cellTypesList;
    }

    @Transactional(readOnly = true)
    @Override
    public CellType getCellType(Long id) {
        Optional<CellType> optionalCellType = cellTypeDao.findById(id, 3);

        if(optionalCellType.isPresent()){
            return optionalCellType.get();
        }

        throw new NoSuchElementException("Cell Type with id: \"" + id +"\" is not exist");
    }

    @Override
    public CellType getInputCellType() {
        Optional<CellType> cellType = cellTypeDao.findCellTypeByTransformType(CellTransformType.INPUT_TO_ANALOG);

        if(!cellType.isPresent()){
            throw new RuntimeException("Can't get input cell type, probably was not created properly yet");
        }

        return cellType.get();
    }

    @Override
    public Variable getVariableByCellTypeAndVariableName(CellType cellType, String variableName) {
        return FunctionService.getVariable(cellType.getFunction(), variableName);
    }

    @Transactional
    @Override
    public void clear() {
        cellTypeDao.deleteAll();
        functionDao.deleteAll();
        variableDao.deleteAll();
    }

    @Override
    public Boolean areCompatible(CellTransformType sourceTransformType, CellTransformType destTransformType) {
        return CellTransformType.areCompatible(sourceTransformType, destTransformType);
    }

    @Override
    public void deleteCellType(Long id) {
        cellTypeDao.deleteById(id);
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

    @Autowired
    public void setIdGeneratorService(IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }
}
