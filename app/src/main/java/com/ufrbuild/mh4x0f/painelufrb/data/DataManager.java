/*
    This file is part of the Painel de Aulas UFRB Open Source Project.
    Painel de Aulas UFRB is licensed under the Apache 2.0.

    Copyright 2019 UFRBuild - Marcos Bomfim

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

package com.ufrbuild.mh4x0f.painelufrb.data;

import android.content.Context;
import com.ufrbuild.mh4x0f.painelufrb.PainelUFRBApp;
import com.ufrbuild.mh4x0f.painelufrb.data.db.database.LogDatabase;
import com.preference.PowerPreference;
import com.preference.Preference;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private static DataManager sInstance;
    public Context context;

    @Inject
    public DataManager(Context context) {
        this.context = context;
    }

//    public static synchronized DataManager getInstance() {
//        if (sInstance == null) {
//            sInstance = new DataManager();
//        }
//        return sInstance;
//    }
    public boolean isFirstTimeLaunch(){
        return getPrefs().getBoolean("FirstTimeLaunch",false);
    }

    public Preference getPrefs() {
        return PowerPreference.defult();
    }

    public LogDatabase getLogDatabse() {
        return LogDatabase.getInstance(PainelUFRBApp.getInstance());
    }


}
