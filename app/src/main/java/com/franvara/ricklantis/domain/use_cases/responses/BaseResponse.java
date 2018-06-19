package com.franvara.ricklantis.domain.use_cases.responses;

import com.franvara.ricklantis.domain.entities.Info;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class BaseResponse {

    @SerializedName("info")
    @Expose
    protected Info info;

    public Info getInfo() {
        return info;
    }

}
