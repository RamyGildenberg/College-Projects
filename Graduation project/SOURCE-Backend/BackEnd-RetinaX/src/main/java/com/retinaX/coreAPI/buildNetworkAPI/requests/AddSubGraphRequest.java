package com.retinaX.coreAPI.buildNetworkAPI.requests;

import com.retinaX.entities.CellInstance;

import java.util.ArrayList;
import java.util.List;

public class AddSubGraphRequest {

    private String name = "";



    //   private Long cellTypeId;
 //   private Long cellInstanceID;

    private List<Long> cellInstanceID;
/*
    public Long getCellTypeId() {
        return cellTypeId;
    }
*/

    /*
    public void setCellInstanceID(Long cellInstanceId) {
        this.cellInstanceID = cellInstanceId;
    }
*/

    public List<Long> getCellInstanceID() {
        return cellInstanceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public Long getCellInstanceID() {
        return cellInstanceID;
    }
*/
    public void setCellInstanceID(List<Long> cellInstanceID) {
        this.cellInstanceID = cellInstanceID;
    }
}
