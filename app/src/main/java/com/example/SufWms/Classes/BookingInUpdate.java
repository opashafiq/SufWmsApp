package com.example.SufWms.Classes;

import java.util.List;

public class BookingInUpdate {
    private List<BookingMasterIn> bm;
    private List<BookingIn> bi;
    private List<Location_Inventory_Mapping> lim;

    public List<BookingMasterIn> getBm() {
        return bm;
    }

    public void setBm(List<BookingMasterIn> bm) {
        this.bm = bm;
    }

    public List<BookingIn> getBi() {
        return bi;
    }

    public void setBi(List<BookingIn> bi) {
        this.bi = bi;
    }

    public List<Location_Inventory_Mapping> getLim() {
        return lim;
    }

    public void setLim(List<Location_Inventory_Mapping> lim) {
        this.lim = lim;
    }
}
