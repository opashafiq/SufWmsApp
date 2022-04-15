package com.example.SufWms.Models;

import com.google.gson.annotations.SerializedName;

public class PartDetails {
    @SerializedName("PartId")
    public String PartId;
    @SerializedName("PartsName")
    public String PartsName;

    public String getPartId() {
        return PartId;
    }

    public void setPartId(String partId) {
        PartId = partId;
    }

    public String getPartsName() {
        return PartsName;
    }

    public void setPartsName(String partsName) {
        PartsName = partsName;
    }
}
