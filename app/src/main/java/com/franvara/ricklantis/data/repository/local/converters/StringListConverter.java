package com.franvara.ricklantis.data.repository.local.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class StringListConverter {

    @TypeConverter
    public List<String> fromJSON(String jsonList){
        return new Gson().fromJson(jsonList, new TypeToken<List<String>>() {}.getType());
    }

    @TypeConverter
    public String toJSON(List<String> stringList){
        return new Gson().toJson(stringList);
    }
}
