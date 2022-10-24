package com.retinaX.services.buildNetwork.cellType;

import com.retinaX.coreAPI.buildNetworkAPI.requests.CreateCellTypeRequest;
import com.retinaX.dal.utils.IdGeneratorService;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.entities.function.Variable;

import java.util.List;

public interface CellTypeService {
    CellType createCellType(CreateCellTypeRequest createCellTypeRequest);

    List<CellType> getExistingCellTypes();

    CellType getCellType(Long id);

    CellType getInputCellType();

    Variable getVariableByCellTypeAndVariableName(CellType cellType, String variableName);

    boolean areCompatible(CellInstance source, CellInstance destination);

    boolean areCompatible(CellType source, CellType destination);

    void clear();

    Boolean areCompatible(CellTransformType sourceTransformType, CellTransformType destTransformType);

    void deleteCellType(Long id);
}
