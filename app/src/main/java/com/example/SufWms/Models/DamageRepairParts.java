package com.example.SufWms.Models;

import com.google.gson.annotations.SerializedName;

public class DamageRepairParts {
    @SerializedName("AutoId")
    public String AutoId ;
    @SerializedName("DamageId")
    public String DamageId ;
    @SerializedName("PartId")
    public String PartId ;
    @SerializedName("Qty")
    public String Qty ;
    @SerializedName("UserId")
    public String UserId ;

    public String getAutoId() {
        return AutoId;
    }

    public void setAutoId(String autoId) {
        AutoId = autoId;
    }

    public String getDamageId() {
        return DamageId;
    }

    public void setDamageId(String damageId) {
        DamageId = damageId;
    }

    public String getPartId() {
        return PartId;
    }

    public void setPartId(String partId) {
        PartId = partId;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
