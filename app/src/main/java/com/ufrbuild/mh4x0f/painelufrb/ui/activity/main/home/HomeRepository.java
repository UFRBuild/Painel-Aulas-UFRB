package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import com.ufrbuild.mh4x0f.painelufrb.data.db.dao.DisciplineDao;
import com.ufrbuild.mh4x0f.painelufrb.data.db.database.DisciplineDatabase;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class HomeRepository {
    public static String TAG = "HomeRepository";
    private DisciplineDao disciplineDao;
    private LiveData<List<Discipline>> allData;


    @Inject
    public HomeRepository(Application application) {
        DisciplineDatabase db = DisciplineDatabase.getDatabase(application);
        disciplineDao = db.disciplineDoa();
        //allData = disciplineDao.getAllDisciplines();
        Log.i(TAG, "HomeRepository: initialize");
    }
    public void setAllData(String id){
        allData = disciplineDao.getAllDisciplinesById(id);
    }

    public LiveData<List<Discipline>> getAllDisciplines() {
        return allData;
    }

    public void insertData(Discipline data) {
        new HomeRepository.DiscInsertion(disciplineDao).execute(data);
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
        new HomeRepository.DiscDeleteAll(disciplineDao).execute(data);
    }

    public void deleteDisciplineByIDAndWeekID(String id, int id_week){
        String[] paraments = {id, String.valueOf(id_week)};
        new HomeRepository.DiscDelete(disciplineDao).execute(paraments);
    }


    private static class DiscDelete extends AsyncTask<String, Void, Void> {

        private DisciplineDao disciplineDaoDao;

        private DiscDelete(DisciplineDao disDao) {

            this.disciplineDaoDao = disDao;

        }

        @Override
        protected Void doInBackground(String ... paraments) {

            disciplineDaoDao.removeDisciplinesByIdAndDayOfWeek(paraments[0], paraments[1]);

            return null;

        }

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
