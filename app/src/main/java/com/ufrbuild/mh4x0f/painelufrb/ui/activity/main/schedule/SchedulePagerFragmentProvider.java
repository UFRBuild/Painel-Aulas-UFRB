package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SchedulePagerFragmentProvider {
    @ContributesAndroidInjector(modules = SchedulePagerFragmentModule.class)
    abstract SchedulePagerFragment provideSchedulePagerFragmentFactory();
}