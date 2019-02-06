package com.denise.postres.com.co.postresnokia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Batter{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("type")
    @Expose
    public String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Batter(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
}

