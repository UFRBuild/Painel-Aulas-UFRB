package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule;
import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import com.ufrbuild.mh4x0f.painelufrb.data.db.dao.DisciplineDao;
import com.ufrbuild.mh4x0f.painelufrb.data.db.database.DisciplineDatabase;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import java.util.List;


public class ScheduleRepository {

    public static String TAG = "ScheduleRepository";
    private DisciplineDao disciplineDao;
    private LiveData<List<Discipline>> allData;


    public ScheduleRepository(Application application, int id) {
        DisciplineDatabase db = DisciplineDatabase.getDatabase(application);
        disciplineDao = db.disciplineDoa();
        allData = disciplineDao.getAllDisciplines(id);
        Log.i(TAG, "ScheduleRepository: initialize");
    }
    public void setAllData(int id){
        allData = disciplineDao.getAllDisciplines(id);
    }

    public LiveData<List<Discipline>> getAllDisciplines() {
        return allData;
    }

    public void insertData(Discipline data) {
        new DiscInsertion(disciplineDao).execute(data);
    }

    private static class DiscInsertion extends AsyncTask<Discipline, Void, Void> {

        private DisciplineDao disciplineDaoDao;

        private DiscInsertion(DisciplineDao disDao) {

            this.disciplineDaoDao = disDao;

        }

        @Override
        protected Void doInBackground(Discipline... data) {

            //disciplineDaoDao.deleteAllData();

            Log.i(TAG, "doInBackground: " + data[0]);
            disciplineDaoDao.insertDetails(data[0]);
            return null;

        }

    }


    public void deleteAllData(Discipline data) {
        new DiscDeleteAll(disciplineDao).execute(data);
    }

    private static class DiscDeleteAll extends AsyncTask<Discipline, Void, Void> {

        private DisciplineDao disciplineDaoDao;

        private DiscDeleteAll(DisciplineDao disDao) {

            this.disciplineDaoDao = disDao;

        }

        @Override
        protected Void doInBackground(Discipline... data) {

            disciplineDaoDao.deleteAllData();

            return null;

        }

    }

}