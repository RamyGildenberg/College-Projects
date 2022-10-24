package com.retinaX.dal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IdGeneratorServiceImplementation  implements IdGeneratorService{

    private IdGenerator idGeneratorDao;

    @Autowired
    public void setIdGeneratorDao(IdGenerator idGeneratorDao) {
        this.idGeneratorDao = idGeneratorDao;
    }

    @Override
    @Transactional
    public Long nextId() {
        IdGeneratorEntity entity = new IdGeneratorEntity();
        Long id = idGeneratorDao.save(entity).getId();
        idGeneratorDao.delete(entity);
        return id;
    }

}
