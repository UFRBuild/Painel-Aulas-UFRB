package com.ufrbuild.mh4x0f.painelufrb.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import java.util.List;

@Dao
public interface DisciplineDao {

    @Insert
    void insertDetails(Discipline data);

    @Query("select * from DisciplineDetails where DayOfWeek = :id")
    LiveData<List<Discipline>> getAllDisciplines(int id);

    @Query("select * from DisciplineDetails where id = :id")
    LiveData<List<Discipline>> getAllDisciplinesById(String id);

    @Query("DELETE from DisciplineDetails where id = :id and DayOfWeek = :id_week")
    void removeDisciplinesByIdAndDayOfWeek(String id, String id_week);

    @Query("DELETE from DisciplineDetails where id = :id")
    void deleteAllDisciplinesById(String id);

    @Query("delete from DisciplineDetails")
    void deleteAllData();

    @Delete
    void delete(Discipline data);

}