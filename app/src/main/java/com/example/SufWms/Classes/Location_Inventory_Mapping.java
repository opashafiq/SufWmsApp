package com.example.SufWms.Classes;

public class Location_Inventory_Mapping {
    private String ILMappingId ;
    private String InventoryId ;
    private String LocationDetailsId ;
    private String Qty ;
    private String UnitId ;
    private String last_user ;
    private String last_update ;

    public String getILMappingId() {
        return ILMappingId;
    }

    public void setILMappingId(String ILMappingId) {
        this.ILMappingId = ILMappingId;
    }

    public String getInventoryId() {
        return InventoryId;
    }

    public void setInventoryId(String inventoryId) {
        InventoryId = inventoryId;
    }

    public String getLocationDetailsId() {
        return LocationDetailsId;
    }

    public void setLocationDetailsId(String locationDetailsId) {
        LocationDetailsId = locationDetailsId;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public String getLast_user() {
        return last_user;
    }

    public void setLast_user(String last_user) {
        this.last_user = last_user;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
