package com.franvara.ricklantis.data.repository.local;

import android.content.Context;

import com.franvara.ricklantis.data.repository.local.daos.CharactersDao;
import com.franvara.ricklantis.domain.entities.Character;

import java.util.List;

public class LocalDataSource implements ILocalDataSource {

    //region Fields
    private final Context context;
    private static CharactersDao charactersDao;

    //endregion

    //region Constructor

    public LocalDataSource(Context context) {
        this.context = context;
        AppDatabase database = AppDatabase.getInstance(this.context.getApplicationContext());
        charactersDao = database.getCharactersDao();
    }

    //endregion

    //region ILocalDataSource implementation

    @Override
    public void saveCharacters(List<Character> characters) {
        charactersDao.bulkInsert(characters);
    }

    @Override
    public void clearCharacters() {
        charactersDao.clear();
    }

    @Override
    public List<Character> getCharacters() {

        return charactersDao.getAll();
    }



    //endregion
}
