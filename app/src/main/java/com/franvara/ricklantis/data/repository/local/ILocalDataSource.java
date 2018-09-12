package com.franvara.ricklantis.data.repository.local;

import com.franvara.ricklantis.domain.entities.Character;

import java.util.List;

public interface ILocalDataSource {

    void saveCharacters(List<Character> characters);
    void clearCharacters();
    List<Character> getCharacters();
    Character getCharacterDetail(int characterId);
}
