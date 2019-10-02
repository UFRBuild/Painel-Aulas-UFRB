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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home;

import android.arch.lifecycle.ViewModelProvider;

import com.ufrbuild.mh4x0f.painelufrb.data.network.services.DisciplineService;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.TimeServerService;
import com.ufrbuild.mh4x0f.painelufrb.di.factory.ViewModelProviderFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeFragmentModule {

    @Provides
    DisciplineService provideDisciplineService(@Named("CRUD") Retrofit retrofit){
        return new DisciplineService(retrofit);
    }


    @Provides
    TimeServerService provideTimeServerService(@Named("Timer") Retrofit retrofit){
        return new TimeServerService(retrofit);
    }

    @Provides
    HomeViewModel homeViewModel(DisciplineService dis, TimeServerService tim) {
        return new HomeViewModel(dis, tim);
    }


    @Provides
    ViewModelProvider.Factory provideViewModelProvider(HomeViewModel viewModel){
        return new ViewModelProviderFactory<>(viewModel);
    }

}
