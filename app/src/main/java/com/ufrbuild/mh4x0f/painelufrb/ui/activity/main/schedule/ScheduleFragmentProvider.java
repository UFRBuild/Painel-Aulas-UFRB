package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ScheduleFragmentProvider {
    @ContributesAndroidInjector(modules = ScheduleFragmentModule.class)
    abstract ScheduleFragment provideScheduleFragmentFactory();
}
