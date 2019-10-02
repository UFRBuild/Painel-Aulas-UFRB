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

package com.ufrbuild.mh4x0f.painelufrb.di.modules;
import android.content.Context;
import com.google.gson.Gson;
import com.ufrbuild.mh4x0f.painelufrb.PainelUFRBApp;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


@Module
public class PainelAppModule {

    @Singleton
    @Provides
    Context provideContext(PainelUFRBApp application){
        return application;
    }


    @Singleton
    @Provides
    DataManager provideDataManager(Context context){
        return new DataManager(context);
    }


    @Singleton
    @Provides
    Gson provideGson(){
        return new Gson();
    }

    @Singleton
    @Provides
    CommonUtils provideCommonUtils(Context context){
        return new CommonUtils(context);
    }

}
