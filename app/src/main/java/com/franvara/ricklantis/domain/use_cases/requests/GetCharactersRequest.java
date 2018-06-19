package com.franvara.ricklantis.domain.use_cases.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCharactersRequest {

    @SerializedName("page")
    @Expose
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
