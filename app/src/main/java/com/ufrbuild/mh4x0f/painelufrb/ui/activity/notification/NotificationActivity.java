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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseActivity;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends BaseActivity<NotificationViewModel> {
    NotificationViewModel viewModel;
    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    CommonUtils utils;
    @Inject
    DataManager mDataManager;
    @BindView(R.id.main_appbar_notification)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mDataManager.getPrefs().getBoolean(getString(R.string.theme_key))) {
            setTheme(R.style.Darktheme);
        }
        else  setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_notification);

        utils.getSupportActionBar(this);


        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.action_title_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public NotificationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(NotificationViewModel.class);
        return viewModel;
    }

    @Override
    protected void setUp() {

    }
}
