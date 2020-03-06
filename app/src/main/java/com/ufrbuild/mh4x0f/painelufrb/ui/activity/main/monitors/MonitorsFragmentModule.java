package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.monitors;

import androidx.lifecycle.ViewModelProvider;

import com.ufrbuild.mh4x0f.painelufrb.data.network.services.DisciplineService;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.TimeServerService;
import com.ufrbuild.mh4x0f.painelufrb.di.factory.ViewModelProviderFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MonitorsFragmentModule {

    @Provides
    DisciplineService provideDisciplineService(@Named("CRUD") Retrofit retrofit){
        return new DisciplineService(retrofit);
    }


    @Provides
    TimeServerService provideTimeServerService(@Named("Timer") Retrofit retrofit){
        return new TimeServerService(retrofit);
    }

//    @Provides
//    HomeRepository provideHomeRepository(PainelUFRBApp application){
//        //TODO: teste this
//        return new HomeRepository(application);
//    }

    @Provides
    MonitorsViewModel monitorsViewModel(DisciplineService dis, TimeServerService tim) {
        return new MonitorsViewModel(dis, tim);
    }


    @Provides
    ViewModelProvider.Factory provideViewModelProvider(MonitorsViewModel viewModel){
        return new ViewModelProviderFactory<>(viewModel);
    }

}