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

package com.ufrbuild.mh4x0f.painelufrb.data.network.services;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.RoomResponse;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class DisciplineService {

    private static final String URL = "https://smsa.ufrb.edu.br/backend/CRUD/";

    private DisciplineApi mDisciplineApi;

    private static DisciplineService instance;


    public static DisciplineService getInstance() {
        if (instance == null) {
            instance = new DisciplineService();
        }
        return instance;
    }

    private DisciplineService() {
        Retrofit mRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).build();
        mDisciplineApi = mRetrofit.create(DisciplineApi.class);
    }

    public DisciplineApi getDisciplineApi() {
        return mDisciplineApi;
    }

    public interface DisciplineApi {
        @POST("getAulas") Call<RoomResponse> getAllMovie(@Body HashMap<String, String> parameters);
    }

}


