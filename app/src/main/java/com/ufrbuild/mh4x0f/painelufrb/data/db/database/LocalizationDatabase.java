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

package com.ufrbuild.mh4x0f.painelufrb.data.db.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.ufrbuild.mh4x0f.painelufrb.data.db.dao.LocalizationDao;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Localization;

@Database(entities = {Localization.class}, version = 3, exportSchema = false)
public abstract class LocalizationDatabase extends RoomDatabase {

    public abstract LocalizationDao localizationDao();

    private static LocalizationDatabase INSTANCE;

    public static LocalizationDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (DisciplineDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                            context, LocalizationDatabase.class, "LOCALIZATION_DATABASE")
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }

        }

        return INSTANCE;

    }
}
