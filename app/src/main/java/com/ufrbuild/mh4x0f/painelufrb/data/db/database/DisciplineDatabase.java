package com.ufrbuild.mh4x0f.painelufrb.data.db.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.ufrbuild.mh4x0f.painelufrb.data.db.dao.DisciplineDao;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;


@Database(entities = {Discipline.class}, version = 3, exportSchema = false)
public abstract class DisciplineDatabase extends RoomDatabase {

    public abstract DisciplineDao disciplineDoa();

    private static DisciplineDatabase INSTANCE;

    public static DisciplineDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (DisciplineDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                            context, DisciplineDatabase.class, "DISCIPLINE_DATABASE")
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }

        }

        return INSTANCE;

    }

}