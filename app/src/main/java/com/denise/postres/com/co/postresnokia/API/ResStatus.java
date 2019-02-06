package com.denise.postres.com.co.postresnokia.API;

import com.google.gson.annotations.SerializedName;

public class ResStatus{
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public ResStatus(String status) {
        this.status = status;
    }
}
