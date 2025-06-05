package com.example.misraices.data.SQLite.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.misraices.data.SQLite.Model.Planta;

import java.util.List;


@Dao
public interface PlantaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPlanta(List<Planta>  planta);

    @Query("SELECT * FROM plantas")
    LiveData<List<Planta>> obtenerTodas();


}
