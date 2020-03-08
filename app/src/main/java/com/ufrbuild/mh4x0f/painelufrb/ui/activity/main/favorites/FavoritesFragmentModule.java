package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites;

import androidx.lifecycle.ViewModelProvider;

import com.ufrbuild.mh4x0f.painelufrb.PainelUFRBApp;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.DisciplineService;
import com.ufrbuild.mh4x0f.painelufrb.data.network.services.TimeServerService;
import com.ufrbuild.mh4x0f.painelufrb.di.factory.ViewModelProviderFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FavoritesFragmentModule {

    @Provides
    DisciplineService provideDisciplineService(@Named("CRUD") Retrofit retrofit){
        return new DisciplineService(retrofit);
    }


    @Provides
    TimeServerService provideTimeServerService(@Named("Timer") Retrofit retrofit){
        return new TimeServerService(retrofit);
    }

    @Provides
    DisciplineRepository provideDisciplineRepository(PainelUFRBApp application){
        //TODO: teste this
        return new DisciplineRepository(application);
    }

    @Provides
    FavoritesViewModel favoritesViewModel(DisciplineService dis, TimeServerService tim, DisciplineRepository repo) {
        return new FavoritesViewModel(dis, tim, repo);
    }


    @Provides
    ViewModelProvider.Factory provideViewModelProvider(FavoritesViewModel viewModel){
        return new ViewModelProviderFactory<>(viewModel);
    }
}
