package com.franvara.ricklantis.domain.entities;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "location_name")
    private String name;
    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "location_url")
    private String url;

    public Location(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
