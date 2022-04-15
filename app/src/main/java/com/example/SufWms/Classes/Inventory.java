package com.example.SufWms.Classes;

public class Inventory {
    private String AutoId ;
    private String BookingId ;
    private String InventoryId ;
    private String SKU ;
    private String ProductDesc ;
    private String Qty ;
    private String UnitId ;
    private String TotalCBM ;
    private String TotalWeight ;
    private String EstimateTakeOutDate ;
    private String ActualTakeOutDate ;
    private String AppuserOut ;
    private String AppUpdateDateOut ;
    private String ASIN ;
    private String ProductName ;
    private String DeliverdQty ;

    public String getAutoId() {
        return AutoId;
    }

    public void setAutoId(String autoId) {
        AutoId = autoId;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getInventoryId() {
        return InventoryId;
    }

    public void setInventoryId(String inventoryId) {
        InventoryId = inventoryId;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
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

    public String getTotalCBM() {
        return TotalCBM;
    }

    public void setTotalCBM(String totalCBM) {
        TotalCBM = totalCBM;
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

    public String getEstimateTakeOutDate() {
        return EstimateTakeOutDate;
    }

    public void setEstimateTakeOutDate(String estimateTakeOutDate) {
        EstimateTakeOutDate = estimateTakeOutDate;
    }

    public String getActualTakeOutDate() {
        return ActualTakeOutDate;
    }

    public void setActualTakeOutDate(String actualTakeOutDate) {
        ActualTakeOutDate = actualTakeOutDate;
    }

    public String getAppuserOut() {
        return AppuserOut;
    }

    public void setAppuserOut(String appuserOut) {
        AppuserOut = appuserOut;
    }

    public String getAppUpdateDateOut() {
        return AppUpdateDateOut;
    }

    public void setAppUpdateDateOut(String appUpdateDateOut) {
        AppUpdateDateOut = appUpdateDateOut;
    }

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDeliverdQty() {
        return DeliverdQty;
    }

    public void setDeliverdQty(String deliverdQty) {
        DeliverdQty = deliverdQty;
    }
}
