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

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.util.Log;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.RoomResponse;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.TimeServer;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.DisciplineService;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.TimeServerService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseViewModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<List<Discipline>> mDisciplineList;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isNetworkError;
    private MutableLiveData<Boolean> isEmptyView;
    private TimeServer mTimerServer;
    private DisciplineService disciplineService;
    private TimeServerService timeService;
    private HomeRepository mRepository;
    private String TAG = "HomeViewModel";

    @Inject
    HomeViewModel(DisciplineService disciplineService, TimeServerService timeService, HomeRepository home) {
        this.disciplineService = disciplineService;
        this.timeService = timeService;
        mDisciplineList = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        isNetworkError = new MutableLiveData<>();
        isEmptyView =  new MutableLiveData<>();
        mTimerServer = new TimeServer();
        mRepository = home;
    }

    public MutableLiveData<List<Discipline>> getDisciplines() {
        return mDisciplineList;
    }
    public MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }
    public MutableLiveData<Boolean> getNetworkErrorStatus() {
        return isNetworkError;
    }
    public MutableLiveData<Boolean> getIsEmptyViewStatus() {
        return isEmptyView;
    }

    public void startRequestAPI() {
        setIsLoading(true);

        // test paraments
        HashMap<String, String> parameters = new HashMap<>();
//        parameters.put("area", "Pavilhão de Aulas 1 - PA1");
//        parameters.put("st_min", "1561496400");
//        parameters.put("st_max", "1561503600");
        parameters.put("area", MainActivity.getInstance().getmSubTitleHome().getText().toString());
        parameters.put("st_min", String.valueOf(mTimerServer.getStart_time_min()));
        parameters.put("st_max", String.valueOf(mTimerServer.getStart_time_max()));

        Call<RoomResponse> DisciplineCall = disciplineService.getDisciplineApi().getAllDiscipline(parameters);
        DisciplineCall.enqueue(new DisciplineCallback());
    }

    public void getDisciplineData() {
        setIsLoading(true);
        timeService.getTimerServerApi().getResult().enqueue(new Callback<TimeServer>() {
            @Override
            public void onResponse(Call<TimeServer> call,
                                   Response<TimeServer> response) {
                TimeServer sensorData = response.body();
                sensorData.Config();
                mTimerServer = sensorData;
                Log.d(TAG, "getStart_time_max: " + sensorData.getStart_time_max());
                Log.d(TAG, "getStart_time_min: " + sensorData.getStart_time_min());
                setIsLoading(false);
                startRequestAPI();
            }

            @Override
            public void onFailure(Call<TimeServer> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.toString());
                setIsLoading(false);
                setIsNetworkError(true);
                showSnackbarMessage(R.string.msg_snack_no_intenet);
            }
        });
    }
    public void showEmptyList() { setDisciplines(Collections.<Discipline>emptyList()); }

    public void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }

    private void setIsNetworkError(boolean status) {
        isNetworkError.postValue(status);
    }

    private void setIsEmptyView(boolean status) {
        isEmptyView.postValue(status);
    }

    private void setDisciplines(List<Discipline> disciplines) {
        setIsLoading(false);
        if (disciplines.size() == 0){
            setIsEmptyView(true);
        }else{
            showSnackbarMessage(R.string.message_update_disciplines);
        }
        mDisciplineList.postValue(disciplines);
    }


    public HomeRepository getmRepository() {
        return mRepository;
    }

    /**
     * Callback
     **/
    private class DisciplineCallback implements Callback<RoomResponse> {

        @Override
        public void onResponse(@NonNull Call<RoomResponse> call, @NonNull Response<RoomResponse> response) {
            RoomResponse roomResponse = response.body();
            Log.i(TAG, "onResponse: " + response.toString());
            if (roomResponse != null) {
                setDisciplines(roomResponse.getDisciplines());
            } else {
                setDisciplines(Collections.<Discipline>emptyList());
                setIsEmptyView(true);
            }
        }

        @Override
        public void onFailure(Call<RoomResponse> call, Throwable t) {
            //setDisciplines(Collections.<Discipline>emptyList());
            setIsNetworkError(true);
            showSnackbarMessage(R.string.msg_snack_no_intenet);
        }
    }
}
