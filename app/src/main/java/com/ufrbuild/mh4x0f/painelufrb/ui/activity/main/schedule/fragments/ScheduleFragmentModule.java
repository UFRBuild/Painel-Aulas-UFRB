package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments;

import androidx.lifecycle.ViewModelProvider;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.DisciplineService;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.TimeServerService;
import com.ufrbuild.mh4x0f.painelufrb.di.factory.ViewModelProviderFactory;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.viewmodels.ScheduleViewModel;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ScheduleFragmentModule {

    @Provides
    DisciplineService provideDisciplineService(@Named("CRUD") Retrofit retrofit){
        return new DisciplineService(retrofit);
    }


    @Provides
    TimeServerService provideTimeServerService(@Named("Timer") Retrofit retrofit){
        return new TimeServerService(retrofit);
    }

//    @Provides
//    ScheduleRepository provideDisciplineRepository(PainelUFRBApp application){
//        //TODO: teste this
//        return new ScheduleRepository(application);
//    }

    @Provides
    ScheduleViewModel scheduleViewModel(DisciplineService dis, TimeServerService tim) {
        return new ScheduleViewModel(dis, tim);
    }


    @Provides
    ViewModelProvider.Factory provideViewModelProvider(ScheduleViewModel viewModel){
        return new ViewModelProviderFactory<>(viewModel);
    }
}
