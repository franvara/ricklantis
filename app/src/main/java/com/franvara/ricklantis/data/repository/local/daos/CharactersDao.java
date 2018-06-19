package com.franvara.ricklantis.data.repository.local.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.franvara.ricklantis.domain.entities.Character;

import java.util.List;

@Dao
public interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Character> characters);

    @Query("SELECT * FROM Characters")
    List<Character> getAll();

    @Query("DELETE FROM Characters")
    void clear();


}
