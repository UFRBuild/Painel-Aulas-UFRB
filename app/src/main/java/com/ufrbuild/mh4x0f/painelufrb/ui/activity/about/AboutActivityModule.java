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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about;

import android.arch.lifecycle.ViewModelProvider;

import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.di.factory.ViewModelProviderFactory;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.details.DetailsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AboutActivityModule {
    @Provides
    AboutViewModel providesViewModel(DataManager store){
        return new AboutViewModel(store);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelProvider(AboutViewModel viewModel){
        return new ViewModelProviderFactory<>(viewModel);
    }
}
