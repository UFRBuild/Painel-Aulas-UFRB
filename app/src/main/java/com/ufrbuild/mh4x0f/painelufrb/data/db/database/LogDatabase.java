package com.ufrbuild.mh4x0f.painelufrb.data.db.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.WorkerThread;
import com.ufrbuild.mh4x0f.painelufrb.data.db.dao.LogDAO;
import com.ufrbuild.mh4x0f.painelufrb.data.db.entity.LogClass;

@Database(entities = {LogClass.class}, version = 2, exportSchema = false)
public abstract class LogDatabase extends RoomDatabase {

    private static LogDatabase sInstance;

    @WorkerThread
    public abstract LogDAO logDao();

    private static LogDatabase initialize(Context context) {
        sInstance = Room.databaseBuilder(context.getApplicationContext(), LogDatabase.class, "log-database").fallbackToDestructiveMigration().build();
        return sInstance;
    }

    public static LogDatabase getInstance(Context context) {
        if (sInstance == null) {
            return initialize(context);
        } else {
            return sInstance;
        }
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public static void addLog(final LogDatabase db, final LogClass log) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                db.logDao().insertAll(log);

            }
        };
        thread.start();
    }


    public static void dropTable(LogDatabase db) {
        db.logDao().dropTable();
    }
}
