package com.retinaX.services.SubGraphNetwork;

import com.retinaX.coreAPI.buildNetworkAPI.requests.AddCellInstanceRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.AddSubGraphRequest;
import com.retinaX.dal.CellInstanceDao;
import com.retinaX.dal.DuplicateGraphDao;
import com.retinaX.dal.SubGraphDao;
import com.retinaX.entities.*;
import com.retinaX.entities.function.Function;
import com.retinaX.entities.function.Variable;
//import com.retinaX.services.utils.CloneManager;
import com.retinaX.services.buildNetwork.BuildNetworkService;
import com.retinaX.services.utils.CloneManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SubGraphServiceImplementation implements SubGraphService{

    @Autowired
    private BuildNetworkService cellDao;

    private SubGraphDao sgDao;
    @Autowired
    private CellInstanceDao ciDao;
    @Autowired
    private DuplicateGraphDao sgCopyDao;



    @Override
    public SubGraphInstance createSubGraph(AddSubGraphRequest request) {
        // Create sub graph object
        // TODO convert the request fully to Sub Graph object
        SubGraphInstance sg = new SubGraphInstance();
//                new Random().nextLong() + 100,
//                request.getName());
        sg.setName(request.getName());
//        List<CellInstance> cellInstances = (List<CellInstance>) ciDao.findAllById(request.getCellInstanceID());
        Iterable<CellInstance> allById = ciDao.findAllById(request.getCellInstanceID());
        List<CellInstance> cellInstancesResult = new ArrayList();
        allById.forEach(cellInstancesResult::add);
        sg.setCells(cellInstancesResult);

        // TODO 2: consider don't give ID to the Sub Graph instance (the db would return it)
        //  SubGraphInstance res = sgDao.save(sg);
        // Saves the sub graph to DB
        SubGraphInstance subGraphInstance = sgDao.save(sg);
        return subGraphInstance;

    }

@Override
public List<SubGraphInstance> getSubGraph(String id) {
    // TODO make it prettier
    List<SubGraphInstance> sg = null;
    try{
        //       sg = sgDao.findAllByName(subGraphFilter.getName()).stream().findFirst().orElseThrow();

        sg = sgDao.findAllByName(id);

    }catch(Exception e){
        e.printStackTrace();
    }

    return sg;

}

    @Override
    public SubGraphInstance cloneSubGraph(Long id) {
        // singleton that manages the clone
        Optional<SubGraphInstance> sg =sgDao.findById(id);
        String name = sg.get().getName();

        SubGraphInstance sgInstance = sgDao.findAllByName(name).get(0);

        CloneManager.reset();
        SubGraphInstance clonedSubGraphInstance = sgInstance.clone();
        sgDao.save(clonedSubGraphInstance);
        return clonedSubGraphInstance;
    }
    @Override
    public List<CellInstance> getCellInstances() {
        //TODO Delete
        return cellDao.getCellInstances();
    }

    @Override
    public List<Connection> getConnections() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void deleteSubGraph(Long id) {

    }

    @Override
    public List<CellInstance> getInputCells() {
        return cellDao.getInputCells();
    }


    @Autowired
    public void setSgDao(SubGraphDao sgDao) {
        this.sgDao = sgDao;
    }
}
