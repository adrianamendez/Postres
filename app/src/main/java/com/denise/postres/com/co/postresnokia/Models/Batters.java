package com.denise.postres.com.co.postresnokia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Batters{

    @SerializedName("batter")
    @Expose
    public List<Batter> batter = null;

    public List<Batter> getBatter() {
        return batter;
    }

    public void setBatter(List<Batter> batter) {
        this.batter = batter;
    }

    public Batters(List<Batter> batter) {
        this.batter = batter;
    }
}
