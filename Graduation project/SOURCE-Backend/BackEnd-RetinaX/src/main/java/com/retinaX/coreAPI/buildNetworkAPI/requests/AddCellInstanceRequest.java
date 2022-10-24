package com.retinaX.coreAPI.buildNetworkAPI.requests;

public class AddCellInstanceRequest {
    private Long cellInstanceID;
    private Long cellTypeId;
    private Double x;
    private Double y;


    public AddCellInstanceRequest() {
    }

    public AddCellInstanceRequest(Long cellTypeId){
        setCellTypeId(cellTypeId);
    }

    public Long getCellTypeId() {
        return cellTypeId;
    }

    public void setCellTypeId(Long cellTypeId) {
        this.cellTypeId = cellTypeId;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Long getCellInstanceID() {
        return cellInstanceID;
    }

    public void setCellInstanceID(Long cellInstanceID) {
        this.cellInstanceID = cellInstanceID;
    }
}
