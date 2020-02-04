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

package com.ufrbuild.mh4x0f.painelufrb.di.builders;

import com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.AboutActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.AboutActivityModule;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.details.DetailsActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.details.DetailsActivityModule;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.donate.DonateActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.donate.DonateActivityModule;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivityModule;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.FavoritesFragmentProvider;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.HomeFragmentProvider;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.monitors.MonitorsFragmentModule;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.monitors.MonitorsFragmentProvider;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.SchedulePagerFragmentProvider;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments.ScheduleFragmentProvider;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.NotificationActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.NotificationActivityModule;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.splash.SplashActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.splash.SplashActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            MonitorsFragmentProvider.class,
            FavoritesFragmentProvider.class,
            SchedulePagerFragmentProvider.class,
            ScheduleFragmentProvider.class,
            HomeFragmentProvider.class})
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector(modules = DonateActivityModule.class)
    abstract DonateActivity contributeDonateActivity();

    @ContributesAndroidInjector(modules = AboutActivityModule.class)
    abstract AboutActivity contributeAboutActivity();

    @ContributesAndroidInjector(modules = DetailsActivityModule.class)
    abstract DetailsActivity contributeDetailsActivity();

    @ContributesAndroidInjector(modules = NotificationActivityModule.class)
    abstract NotificationActivity contributeNotificationActivity();

}
