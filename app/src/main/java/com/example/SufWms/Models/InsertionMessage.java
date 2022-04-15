package com.example.SufWms.Models;

import com.google.gson.annotations.SerializedName;

public class InsertionMessage {
    @SerializedName("InsertedId")
    public String InsertedId ;
    @SerializedName("StrMessage")
    public String StrMessage ;

    public String getInsertedId() {
        return InsertedId;
    }

    public void setInsertedId(String insertedId) {
        InsertedId = insertedId;
    }

    public String getStrMessage() {
        return StrMessage;
    }

    public void setStrMessage(String strMessage) {
        StrMessage = strMessage;
    }
}
