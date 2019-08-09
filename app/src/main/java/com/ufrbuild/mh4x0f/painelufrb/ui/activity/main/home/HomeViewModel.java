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

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.RoomResponse;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.TimeServer;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.DisciplineService;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.TimeServerService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<List<Discipline>> mDisciplineList;
    private MutableLiveData<Boolean> isLoading;
    private TimeServer mTimerServer;
    private DisciplineService disciplineService;
    private TimeServerService timeService;
    private String TAG = "HomeViewModel";

    HomeViewModel(DisciplineService disciplineService, TimeServerService timeService) {
        this.disciplineService = disciplineService;
        this.timeService = timeService;
        mDisciplineList = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        mTimerServer = new TimeServer();
    }

    public MutableLiveData<List<Discipline>> getDisciplines() {
        return mDisciplineList;
    }
    public MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }

    public void loadMoviesNetwork() {
        setIsLoading(true);

        // test paraments
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("area", "Pavilhão de Aulas 1 - PA1");
        parameters.put("st_min", "1561496400");
        parameters.put("st_max", "1561503600");
//        parameters.put("area", MainActivity.getInstance().getmSubTitleHome().getText().toString());
//        parameters.put("st_min", String.valueOf(mTimerServer.getStart_time_min()));
//        parameters.put("st_max", String.valueOf(mTimerServer.getStart_time_max()));


        Call<RoomResponse> DisciplineCall = disciplineService.getDisciplineApi().getAllMovie(parameters);
        DisciplineCall.enqueue(new DisciplineCallback());
    }
    public void loadMovieLocal() {
        setIsLoading(true);
//        Call<TimeServer> movieCall2 = timeService.getMovieApi().getResult();
//        movieCall2.enqueue(new TimerCallback());


        timeService.getMovieApi().getResult().enqueue(new Callback<TimeServer>() {
            @Override
            public void onResponse(Call<TimeServer> call,
                                   Response<TimeServer> response) {
                TimeServer sensorData = response.body();
                sensorData.Config();
                mTimerServer = sensorData;
                Log.d("result", "getStart_time_max: " + sensorData.getStart_time_max());
                Log.d("result", "getStart_time_min: " + sensorData.getStart_time_min());
                setIsLoading(false);
                loadMoviesNetwork();
            }

            @Override
            public void onFailure(Call<TimeServer> call, Throwable t) {

                Log.d("result", "onFailure: " + t.toString());
                setIsLoading(false);

            }
        });
    }
    public void showEmptyList() { setMovies(Collections.<Discipline>emptyList()); }

    private void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }
    private void setMovies(List<Discipline> disciplines) {
        setIsLoading(false);
        mDisciplineList.postValue(disciplines);
    }


    private class TimerCallback implements Callback<TimeServer> {

        @Override
        public void onResponse(@NonNull Call<TimeServer> call, @NonNull Response<TimeServer> response) {
            TimeServer mTimerResponse = response.body();
            Log.i("result", "onResponse: " + mTimerResponse.getResult());
//            if (movieResponse != null) {
//                setMovies(movieResponse.getDisciplines());
//            } else {
//                setMovies(Collections.<Discipline>emptyList());
//            }
        }

        @Override
        public void onFailure(Call<TimeServer> call, Throwable t) {
            //setMovies(Collections.<Discipline>emptyList());
            Log.i("result", "onFailure: " +t.toString());

        }
    }


    /**
     * Callback
     **/
    private class DisciplineCallback implements Callback<RoomResponse> {

        @Override
        public void onResponse(@NonNull Call<RoomResponse> call, @NonNull Response<RoomResponse> response) {
            RoomResponse roomResponse = response.body();
            Log.i("result", "onResponse: " + response.toString());
            if (roomResponse != null) {
                setMovies(roomResponse.getDisciplines());
            } else {
                setMovies(Collections.<Discipline>emptyList());
            }
        }

        @Override
        public void onFailure(Call<RoomResponse> call, Throwable t) {
            setMovies(Collections.<Discipline>emptyList());

        }
    }
}
