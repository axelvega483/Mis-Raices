package com.example.misraices.data.SQLite.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.misraices.data.SQLite.DAO.PlantaDAO;
import com.example.misraices.data.SQLite.Model.Planta;

@Database(entities = {Planta.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instancia;

    public abstract PlantaDAO plantaDao();

    public static synchronized AppDatabase getInstancia(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "plantas_db").fallbackToDestructiveMigration().build();
        }
        return instancia;
    }
}