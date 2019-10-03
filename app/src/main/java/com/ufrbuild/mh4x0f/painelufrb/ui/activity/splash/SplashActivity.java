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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.splash;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseActivity;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import javax.inject.Inject;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    @BindView(R.id.img_logo_splash)
    ImageView mImageLogo;

    SplashViewModel viewModel;
    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    CommonUtils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        utils.getSupportActionBar(this);

        Animation fadEffect = AnimationUtils.loadAnimation(this, R.anim.effect_splash);
        //mTvDescription.startAnimation(fadEffect);
        mImageLogo.startAnimation(fadEffect);

    }

    @Override
    public SplashViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        return viewModel;
    }

    @Override
    protected void setUp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
