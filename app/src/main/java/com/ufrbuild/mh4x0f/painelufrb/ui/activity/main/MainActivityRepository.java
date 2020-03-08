/*
    This file is part of the Painel de Aulas UFRB Open Source Project.
    Painel de Aulas UFRB is licensed under the Apache 2.0.

    Copyright 2019/2020 UFRBuild - Marcos Bomfim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ufrbuild.mh4x0f.painelufrb.data.db.dao.LocalizationDao;
import com.ufrbuild.mh4x0f.painelufrb.data.db.database.LocalizationDatabase;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Localization;


@Singleton
public class MainActivityRepository {
    public static String TAG = "MainActivityRepository";
    private LocalizationDao localizationDao;
    private LiveData<List<Localization>> allLocalization;
    private List<Localization> localCampus;
    private MutableLiveData<Boolean> isLoading;


    @Inject
    public MainActivityRepository(Application application) {
        LocalizationDatabase db = LocalizationDatabase.getDatabase(application);
        localizationDao = db.localizationDao();
        isLoading = new MutableLiveData<>();
        allLocalization = localizationDao.getAllLocalization();
        Log.i(TAG, "MainActivityRepository: initialize");
    }


    public List<Localization> getLocalCampus() {
        return localCampus;
    }

    public void setLocalCampus(List<Localization> localCampus) {
        this.localCampus = localCampus;
    }

    public LiveData<List<Localization>> getAllLocalizations() {
        return allLocalization;
    }


    public void insertAllLocalization(List<Localization> data) {
        new MainActivityRepository.insertAllLocalization(localizationDao).execute(data);
    }


    private static class insertAllLocalization extends AsyncTask<List<Localization>, Void, Void> {

        private LocalizationDao localzationDaoDao;

        private insertAllLocalization(LocalizationDao localDao) {

            this.localzationDaoDao = localDao;

        }

        @Override
        protected Void doInBackground(List<Localization>... data) {

            localzationDaoDao.deleteAllLocalization();

            Log.i(TAG, "doInBackground: " + data[0]);
            localzationDaoDao.insertAll(data[0]);

            return null;

        }

    }


}
