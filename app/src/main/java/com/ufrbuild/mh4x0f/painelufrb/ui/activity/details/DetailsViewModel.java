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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseViewModel;

import javax.inject.Inject;

public class DetailsViewModel extends BaseViewModel {

    MutableLiveData<Discipline> mDisciplines;

    private DataManager store;

    @Inject
    public DetailsViewModel(DataManager store) {
        this.store = store;
        this.mDisciplines = new MutableLiveData<>();
    }

    public void loadData(Intent intent) {
        assert intent.getExtras() != null;
        Discipline disciplineExtra = intent.getExtras().getParcelable("item");

        mDisciplines.postValue(disciplineExtra);
    }

    public MutableLiveData<Discipline> getDisciplines() {
        return mDisciplines;
    }
}
