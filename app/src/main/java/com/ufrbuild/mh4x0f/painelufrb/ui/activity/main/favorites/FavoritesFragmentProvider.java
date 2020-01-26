package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites;

import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.HomeFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.HomeFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavoritesFragmentProvider {
    @ContributesAndroidInjector(modules = FavoritesFragmentModule.class)
    abstract FavoritesFragment provideFavoritesFragmentFactory();
}
