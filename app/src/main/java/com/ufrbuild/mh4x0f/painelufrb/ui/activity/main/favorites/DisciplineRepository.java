package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import com.ufrbuild.mh4x0f.painelufrb.data.db.dao.DisciplineDao;
import com.ufrbuild.mh4x0f.painelufrb.data.db.database.DisciplineDatabase;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DisciplineRepository {

    public static String TAG = "DisciplineRepository";
    private DisciplineDao disciplineDao;
    private LiveData<List<Discipline>> allData;

    @Inject
    public DisciplineRepository(Application application) {
        DisciplineDatabase db = DisciplineDatabase.getDatabase(application);
        disciplineDao = db.disciplineDoa();
        //allData = disciplineDao.getDetails();
        Log.i(TAG, "DisciplineRepository: initialize");
    }

    public LiveData<List<Discipline>> getAllData() {
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

            Log.i(TAG, "doInBackground: " + data[0].getName());
            disciplineDaoDao.insertDetails(data[0]);
            return null;

        }

    }

}
