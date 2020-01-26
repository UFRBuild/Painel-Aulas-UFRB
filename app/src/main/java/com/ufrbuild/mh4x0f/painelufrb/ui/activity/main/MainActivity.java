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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.AboutActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.donate.DonateActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.FavoritesFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.HomeFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.HomeViewModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.models.LocateModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.Schedule;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.ScheduleFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.NotificationActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.*;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseViewModel;
import com.ufrbuild.mh4x0f.painelufrb.utils.BottomNavigationBehaviour;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import javax.inject.Inject;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class MainActivity extends BaseActivity<MainActivityViewModel> {


    @Inject
    MainActivityViewModel viewModel;

    @Inject
    CommonUtils utils;

    @Inject
    DataManager mDataManager;

    public final static int ITEM_MATERIALDRAWER_FAVORITES = 2;
    public final static int ITEM_MATERIALDRAWER_MARK = 4;
    public final static int ITEM_MATERIALDRAWER_DONATE = 10;
    public final static int ITEM_MATERIALDRAWER_ABOUT = 11;
    public final static int ITEM_MATERIALDRAWER_AVISOS = 3;
    private static final String TAG = "MainActivity";
    private BaseFragment mActiveFragment;
    @BindView(R.id.subtitle_home)
    TextView mSubTitleHome;

    @BindView(R.id.ll_subtitle_home)
    LinearLayout mllSubtTitleHome;

    private Drawer mMenuSideBar;


    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;


    @BindView(R.id.navigation_button)
    BottomNavigationView bottomNavigationView;

    // Singleton instance
    private static MainActivity sInstance = null;

    public TextView getmSubTitleHome() {
        return mSubTitleHome;
    }

    public Drawer getmMenuSideBar() {
        return mMenuSideBar;
    }

    // Getter to access Singleton instance
    public static MainActivity getInstance() {
        return sInstance ;
    }

    public FloatingSearchView getmSearchView() {
        return mSearchView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mDataManager.getPrefs().getBoolean(getString(R.string.theme_key))) {
            setTheme(R.style.Darktheme);
        }
        else  setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        sInstance = this;
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initScrollAutoHide();

        // load local localization
        LocateModel locate = mDataManager.getPrefs().getObject(getString(R.string.locate_campus), LocateModel.class);
        if (locate != null){
            mSubTitleHome.setText(locate.getTitle());
        }

        setupMaterialDrawer(savedInstanceState);

        // get support action bar mode
        utils.getSupportActionBar(this);

        mActiveFragment = new HomeFragment();


        // get firebase token to send message for just test app
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//                        Log.d(TAG, token);
//                    }
//                });

        //I added this if statement to keep the selected fragment when rotating the device
        loadFragmentCommit(mActiveFragment);


        mllSubtTitleHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleSearchDialogCompat(MainActivity.this, getString(R.string.title_name_dialog),
                        getString(R.string.title_select_area), null, utils.getAllLocateModel(MainActivity.this),
                        new SearchResultListener<LocateModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   LocateModel item, int position) {
                                try {
                                    if (item != null){
                                        //TODO : fix fragment load
                                        mDataManager.getPrefs().putObject(getString(R.string.locate_campus), item);
                                        getmSubTitleHome().setText(item.getTitle());
                                        mActiveFragment.getViewModel().getDisciplineData();
                                    }
                                }
                                catch (Exception e){
                                    Log.i(TAG, "onSelected: " + e.toString());
                                }
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        if (getIntent() != null && getIntent().getExtras() != null) {
            call_Activity(getApplicationContext(), NotificationActivity.class);
        }

    }

    private void initScrollAutoHide(){
        // Navigation behaviour - hide/show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());
    }


    public void loadFragmentCommit(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    //This won't show a blank page when android back press button is clicked from fragment
                    //.addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_home_fragment:
                fragment = new HomeFragment();
                loadFragmentCommit(fragment);
                return true;
            case R.id.navigation_favorites_fragment:
                fragment = new FavoritesFragment();
                loadFragmentCommit(fragment);
                return true;
            case R.id.navigation_monitors_fragment:
                //fragment = new ProfileFragment();
                //loadFragmentCommit(fragment);
                return true;
            case R.id.navigation_schedule_fragment:
                fragment = new Schedule();
                loadFragmentCommit(fragment);
                return true;
        }
        return false;
    };




    public void setupMaterialDrawer( Bundle state){
        // mount material drawer menu
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .addProfiles(
                        new ProfileDrawerItem().
                                withName(getString(R.string.app_name))
                                .withEmail(getString(R.string.team_developers)).withIcon(getResources().
                                getDrawable(R.mipmap.ic_launcher_round)).
                                withTypeface(getDefaultFont())
                )
                .withSavedInstance(state)
                .build();

        // TODO: fix the number id witch item menudrawer
        PrimaryDrawerItem item_fragment_painel = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.home_painel).withIcon(GoogleMaterial.Icon.gmd_perm_media);
        PrimaryDrawerItem item_favorities = new PrimaryDrawerItem().withIdentifier(ITEM_MATERIALDRAWER_FAVORITES).withName(R.string.home_favorites).withIcon(GoogleMaterial.Icon.gmd_favorite_border);
        SecondaryDrawerItem item_ac_settings = new SecondaryDrawerItem().withName(R.string.home_settings).withIcon(GoogleMaterial.Icon.gmd_settings_applications).withSelectable(false);
        SecondaryDrawerItem item_ac_about = new SecondaryDrawerItem().
                withIdentifier(ITEM_MATERIALDRAWER_ABOUT).withName(R.string.home_about).
                withIcon(GoogleMaterial.Icon.gmd_apps).withSelectable(false);
        SecondaryDrawerItem item_add_markers = new SecondaryDrawerItem().withIdentifier(ITEM_MATERIALDRAWER_DONATE).withName(R.string.home_makator).withIcon(GoogleMaterial.Icon.gmd_note_add).withSelectable(false);
        SecondaryDrawerItem item_notification = new SecondaryDrawerItem().withIdentifier(ITEM_MATERIALDRAWER_AVISOS).withName(R.string.home_notification).withIcon(GoogleMaterial.Icon.gmd_notifications).withSelectable(false);

        if (mDataManager.getNotificationsMessage() != null) {
            if (mDataManager.getNotificationsMessage().size() != 0) {
                item_notification.withBadge(
                        String.valueOf(mDataManager.getNotificationsMessage().size())
                ).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700));
            }
        }

        //create the drawer and remember the `Drawer` result object
        mMenuSideBar = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item_fragment_painel.withTypeface(getDefaultFont()),
                        item_favorities.withTypeface(getDefaultFont()),
                        item_notification.withTypeface(getDefaultFont()),
                        new SectionDrawerItem().withName(R.string.home_marcadores).withTypeface(getDefaultFont()),
                        item_add_markers.withTypeface(getDefaultFont()),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName(R.string.home_modo_escuro).
                                withIcon(GoogleMaterial.Icon.gmd_colorize).withChecked(mDataManager.getPrefs().getBoolean(getString(R.string.theme_key))).
                                withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false)
                                .withTypeface(getDefaultFont()),
                        item_ac_settings.withTypeface(getDefaultFont()),
                        new SecondaryDrawerItem().withName(R.string.home_feedback)
                                .withIcon(GoogleMaterial.Icon.gmd_feedback).withSelectable(false)
                                .withTypeface(getDefaultFont()),
                        new SecondaryDrawerItem().
                                withIdentifier(ITEM_MATERIALDRAWER_DONATE).withName(R.string.home_donate)
                                .withIcon(GoogleMaterial.Icon.gmd_favorite)
                                .withSelectable(false)
                                .withTypeface(getDefaultFont()),
                        item_ac_about.withTypeface(getDefaultFont())
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Log.i(TAG, "onItemClick: " + position);
                        switch ((int)drawerItem.getIdentifier()) {
                            case ITEM_MATERIALDRAWER_ABOUT:
                                call_Activity(getApplicationContext(), AboutActivity.class);
                                break;
                            case ITEM_MATERIALDRAWER_DONATE:
                                call_Activity(getApplicationContext(), DonateActivity.class);
                                break;
                            case ITEM_MATERIALDRAWER_AVISOS:
                                call_Activity(getApplicationContext(), NotificationActivity.class);
                                break;
                            case ITEM_MATERIALDRAWER_FAVORITES:
                                call_Activity(getApplicationContext(), DonateActivity.class);
                                break;
                            case ITEM_MATERIALDRAWER_MARK:
                                call_Activity(getApplicationContext(), DonateActivity.class);
                                break;
                        }
                        return true;
                    }
                })
                .build();
    }

    public Typeface getDefaultFont(){
        return Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                mDataManager.getPrefs().putBoolean(getString(R.string.theme_key), isChecked);
                restartApp();
            }
        }
    };

    @Override
    public MainActivityViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void setUp() {
        Log.i(TAG, "setUp: ");
    }

    public void restartApp () {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

    public void call_Activity (Context context, Class<?> ac) {
        // about activity
        Intent i = new Intent(context,ac);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (mMenuSideBar.isDrawerOpen()){
            mMenuSideBar.closeDrawer();
            return;
        }
        if (mSearchView.isSearchBarFocused()){
            mSearchView.clearSearchFocus();
            mSearchView.hideProgress();
            return;
        }
        super.onBackPressed();
    }
}
