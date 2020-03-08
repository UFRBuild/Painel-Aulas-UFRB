package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments.ScheduleFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments.ScheduleFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ScheduleFragmentProvider {
    @ContributesAndroidInjector(modules = ScheduleFragmentModule.class)
    abstract ScheduleFragment provideScheduleFragmentFactory();
}
