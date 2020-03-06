package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.viewmodels;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.TimeServer;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.DisciplineService;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.TimeServerService;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.ScheduleRepository;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.SectionHeader;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseViewModel;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class ScheduleViewModel extends BaseViewModel {
    private MutableLiveData<List<SectionHeader>> mDisciplineSectionedList;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isNetworkError;
    private MutableLiveData<Boolean> isEmptyView;
    private TimeServer mTimerServer;
    private DisciplineService disciplineService;
    private TimeServerService timeService;
    private ScheduleRepository mRepository;
    private LiveData<List<Discipline>> allDisciplines;
    private String TAG = "ScheduleViewModel";

    @Inject
    public ScheduleViewModel(DisciplineService disciplineService, TimeServerService timeService) {
        this.disciplineService = disciplineService;
        this.timeService = timeService;
        mDisciplineSectionedList = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        isNetworkError = new MutableLiveData<>();
        isEmptyView =  new MutableLiveData<>();
        mTimerServer = new TimeServer();
        //allDisciplines = mRepository.getAllDisciplines();
    }

    public MutableLiveData<List<SectionHeader>> getDisciplines() {
        return mDisciplineSectionedList;
    }

    public ScheduleRepository getmRepository() {
        return mRepository;
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


    LiveData<List<Discipline>> getAllDisciplines() {
        return allDisciplines;
    }


    public void getDisciplineData(List<Discipline> data) {
        setIsLoading(true);
        //List<Discipline> data  = mRepository.getAllData();
        List<SectionHeader> sectioned_dis = new ArrayList<SectionHeader>();
        List<Discipline> data_manha =  new ArrayList<Discipline>();
        List<Discipline> data_tarde =  new ArrayList<Discipline>();
        List<Discipline> data_noite =  new ArrayList<Discipline>();


        String start_time_converted;
        int hours;
        for (Discipline item : data) {
            start_time_converted  = CommonUtils.intToTimeString(item.getStart_time(), -3);
            hours = Integer.parseInt(start_time_converted.split("h")[0]);
            Log.i(TAG, "getDisciplineData: " + item.getName() + " h: " + hours);
            if(hours >= 1 && hours <= 12){
                if (data_manha.isEmpty()){
                    sectioned_dis.add(new SectionHeader(data_manha, "Manhã"));
                }
                data_manha.add(item);
            }else if(hours >= 12 && hours <= 17){
                if (data_tarde.isEmpty()){
                    sectioned_dis.add(new SectionHeader(data_tarde, "Tarde"));
                }
                data_tarde.add(item);
            }else if(hours >= 18 && hours <= 24){
                if (data_noite.isEmpty()){
                    sectioned_dis.add(new SectionHeader(data_noite, "Noite"));
                }
                data_noite.add(item);
            }
        }
        Collections.sort(data_manha);
        Collections.sort(data_tarde);
        Collections.sort(data_noite);

        setDisciplines(sectioned_dis);

    }
    public void showEmptyList() { setDisciplines(Collections.<SectionHeader>emptyList()); }

    private void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }

    private void setIsNetworkError(boolean status) {
        isNetworkError.postValue(status);
    }

    private void setIsEmptyView(boolean status) {
        isEmptyView.postValue(status);
    }

    private void setDisciplines(List<SectionHeader> disciplines_sectioned) {
        setIsLoading(false);
        if (disciplines_sectioned.size() == 0){
            setIsEmptyView(true);
        }
        mDisciplineSectionedList.postValue(disciplines_sectioned);
    }
}
