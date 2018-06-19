package com.franvara.ricklantis.domain.use_cases.responses;

import com.franvara.ricklantis.domain.entities.Character;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCharactersResponse extends BaseResponse {

    @SerializedName("results")
    @Expose
    private List<Character> characters = null;

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public GetCharactersResponse(List<Character> characters) {
        this.characters = characters;
    }
}
