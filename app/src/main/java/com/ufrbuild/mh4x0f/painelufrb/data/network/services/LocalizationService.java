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

package com.ufrbuild.mh4x0f.painelufrb.data.network.services;

import com.ufrbuild.mh4x0f.painelufrb.data.network.model.LocalizationResponse;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

@Singleton
public class LocalizationService {
    private LocalizationService.LocalizationApi mLocalizationApi;

    @Inject
    public LocalizationService(@Named("Gists") Retrofit retrofit) {
        mLocalizationApi = retrofit.create(LocalizationService.LocalizationApi.class);
    }

    public LocalizationService.LocalizationApi getmLocalizationApi() {
        return mLocalizationApi;
    }

    public interface LocalizationApi {
        @GET("painel-aulas-ufrb-localization.json")
        Call<LocalizationResponse> getAllLocalization();
    }

}
