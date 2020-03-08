package com.ufrbuild.mh4x0f.painelufrb.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.ufrbuild.mh4x0f.painelufrb.data.db.entity.LogClass;

import java.util.List;

@Dao
public interface LogDAO {

    @Query("SELECT * FROM LogClass")
    List<LogClass> getAll();

    @Query("DELETE FROM LogClass")
    void dropTable();

    @Insert
    void insertAll(LogClass... logs);

    @Delete
    void delete(LogClass log);
}
