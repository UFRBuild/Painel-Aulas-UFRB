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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.widget.TextView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseActivity;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends BaseActivity<DetailsViewModel> {

    @BindView(R.id.image) AppCompatImageView image;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.desc) TextView desc;

    @Inject
    DataManager mDataManager;
    @Inject
    CommonUtils utils;

    DetailsViewModel viewModel;
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mDataManager.getPrefs().getBoolean(getString(R.string.theme_key))) {
            setTheme(R.style.Darktheme);
        }
        else  setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);


        // get support action bar mode
        utils.getSupportActionBar(this);



    }

    @Override
    public DetailsViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel.class);
        return viewModel;
    }

    @Override
    protected void setUp() {
        viewModel.getDisciplines().observe(this, new ClasRoomObserver());

        viewModel.loadData(getIntent());
    }

    private class ClasRoomObserver implements Observer<Discipline> {
        @Override
        public void onChanged(@Nullable Discipline discipline) {
            if (discipline == null) return;

            title.setText(discipline.getName());
            desc.setText(discipline.getDescription());
        }
    }

}

