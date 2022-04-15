package com.example.SufWms.Classes;

public class LocationDetails {
    private String Id ;
    private String LocationId ;
    private String Details ;
    private String BarcodeNo ;
    private String IsAvailable ;
    private String last_user ;
    private String last_update ;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLocationId() {
        return LocationId;
    }

    public void setLocationId(String locationId) {
        LocationId = locationId;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getBarcodeNo() {
        return BarcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        BarcodeNo = barcodeNo;
    }

    public String getIsAvailable() {
        return IsAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        IsAvailable = isAvailable;
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
