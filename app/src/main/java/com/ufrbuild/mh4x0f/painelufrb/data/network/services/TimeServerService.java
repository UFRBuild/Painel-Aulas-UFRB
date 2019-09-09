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

import android.os.Build;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.TimeServer;
import com.ufrbuild.mh4x0f.painelufrb.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class TimeServerService {
    private static final String URL = "https://smsa.ufrb.edu.br/backend/Time/";

    private TimeServerService.TimerApi mTimerApi;

    private static TimeServerService instance;


    public static TimeServerService getInstance() {
        if (instance == null) {
            instance = new TimeServerService();
        }

        return instance;
    }


    private TimeServerService() {
        Retrofit mRetrofit;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mRetrofit = new Retrofit.Builder().
                    addConverterFactory(GsonConverterFactory.create())
                    .client(NetworkUtils.getUnsafeOkHttpClient().build())
                    .baseUrl(URL).build();
        }
        else{
            mRetrofit = new Retrofit.Builder().
                    addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).build();
        }
        mTimerApi = mRetrofit.create(TimerApi.class);
    }

    public TimeServerService.TimerApi getTimerServerApi() {
        return mTimerApi;
    }

    public interface TimerApi {
        @GET("getTime")
        Call<TimeServer> getResult();
    }

}
