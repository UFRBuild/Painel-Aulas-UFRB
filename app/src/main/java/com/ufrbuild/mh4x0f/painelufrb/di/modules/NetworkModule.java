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

package com.ufrbuild.mh4x0f.painelufrb.di.modules;
import android.os.Build;
import com.ufrbuild.mh4x0f.painelufrb.utils.AppConstants;
import com.ufrbuild.mh4x0f.painelufrb.utils.NetworkUtils;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static int REQUEST_TIMEOUT = 10;
    private static OkHttpClient okHttpClient;

    @Singleton
    @Provides
    final OkHttpClient providesClient(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            okHttpClient = NetworkUtils.getUnsafeOkHttpClient().build();
        }
        else{
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }

        return okHttpClient;
    }

    @Singleton
    @Provides
    @Named("CRUD")
    Retrofit provideRetrofitCRUD(OkHttpClient client){
        Retrofit mRetrofit;
        mRetrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(AppConstants.API_URL_Discipline).build();

        return mRetrofit;
    }


    @Singleton
    @Provides
    @Named("Timer")
    Retrofit provideRetrofitTimer(OkHttpClient client){
        Retrofit mRetrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(AppConstants.API_URL_Timer).build();
        return mRetrofit;
    }

    @Singleton
    @Provides
    @Named("Gists")
    Retrofit provideRetrofitGists(OkHttpClient client){
        Retrofit mRetrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(AppConstants.API_URL_GIST).build();
        return mRetrofit;
    }

//
//    @Singleton
//    @Provides
//    ApiService provideApiService(Retrofit retrofit){
//        return retrofit.create(ApiService.class);
//    }
}
