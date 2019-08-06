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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.HomeFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseViewModel;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.*;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends BaseActivity {


    private static final String TAG = "result";
    private HomeFragment mHomeFragment;
    @BindView(R.id.subtitle_home)
    TextView mSubTitleHome;

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    DataManager mDataManager;

    // Singleton instance
    private static MainActivity sInstance = null;

    public TextView getmSubTitleHome() {
        return mSubTitleHome;
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

        mDataManager = DataManager.getInstance();
        if(mDataManager.getPrefs().getBoolean("DarkModeTheme")) {
            setTheme(R.style.Darktheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sInstance = this;
        ButterKnife.bind(this);


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .addProfiles(
                        new ProfileDrawerItem().withName("Painel de Aulas - UFRB").withEmail("UFRBuild").withIcon(getResources().getDrawable(R.mipmap.ic_launcher_round))
                )
                .withSavedInstance(savedInstanceState)
                .build();


        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Painel").withIcon(GoogleMaterial.Icon.gmd_perm_media);
        PrimaryDrawerItem item_favorities = new PrimaryDrawerItem().withIdentifier(2).withName("Favoritos").withIcon(GoogleMaterial.Icon.gmd_favorite_border);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(3).withName("Configurações").withIcon(GoogleMaterial.Icon.gmd_settings_applications);
        SecondaryDrawerItem item_add_markers = new SecondaryDrawerItem().withIdentifier(4).withName("Criar novo marcador").withIcon(GoogleMaterial.Icon.gmd_note_add);



//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        item_favorities,
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        item_add_markers,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withIdentifier(5).withName("Doar").withIcon(GoogleMaterial.Icon.gmd_favorite),
                        item2,
                        new SecondaryDrawerItem().withIdentifier(5).withName("Ajuda e Feedback").withIcon(GoogleMaterial.Icon.gmd_feedback)
                        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();

        // support change color statusbar for API < 21
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            window.setStatusBarColor(color);
        }

        mHomeFragment = new HomeFragment();


        // dialog discipline test
//        HashMap data = new  HashMap<String, String>();
//        data.put("matter", "Example Dialog discinpline");
//        DialogItemClassRoom generalDialogFragment =
//                DialogItemClassRoom.newInstance(data);
//        generalDialogFragment.show(getSupportFragmentManager(),"dialog");


        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

    }

    @Override
    public BaseViewModel getViewModel() {
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem actiondarkmode = menu.findItem(R.id.action_darkmode);
        actiondarkmode.setChecked(DataManager.getInstance().getPrefs().getBoolean("DarkModeTheme"));


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

        switch (item.getItemId()) {
            case R.id.action_darkmode:
                if (!mDataManager.getPrefs().getBoolean("DarkModeTheme"))
                    mDataManager.getPrefs().putBoolean("DarkModeTheme", true);
                else
                    mDataManager.getPrefs().putBoolean("DarkModeTheme", false);
                restartApp();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public HomeViewModel getViewModel() {
//        return viewModel;
//    }

    @Override
    protected void setUp() {
        Log.i(TAG, "setUp: ");
    }

    public void restartApp () {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}
