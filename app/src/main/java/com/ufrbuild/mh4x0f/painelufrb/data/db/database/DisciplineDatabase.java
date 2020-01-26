package com.ufrbuild.mh4x0f.painelufrb.data.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
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