package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.monitors;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public  abstract class MonitorsFragmentProvider  {
    @ContributesAndroidInjector(modules = MonitorsFragmentModule.class)
    abstract MonitorsFragment provideMonitorsFragmentFactory();
}
