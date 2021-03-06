/*
    This file is part of the Painel de Aulas UFRB Open Source Project.
    Painel de Aulas UFRB is licensed under the Apache 2.0.

    Copyright 2019/2020 UFRBuild - Marcos Bomfim

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

package com.ufrbuild.mh4x0f.painelufrb.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Localization;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivityViewModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.models.LocateModel;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CommonUtils {
    public Context context;

    @Inject
    DataManager mDataManager;

    @Inject
    public CommonUtils(Context context) {
        this.context = context;
    }


    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void getSupportActionBar(Activity ac){

        // support change color statusbar for API < 21
        Window window = ac.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            ac.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            window.setStatusBarColor(color);
        }
    }



    public static String getFormattedDate( long smsTimeInMilis) {
        // https://stackoverflow.com/questions/12818711/how-to-find-time-is-today-or-yesterday-in-android/16146263
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return " Hoje " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return " Ontem " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return " " + DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return " " + DateFormat.format("dd MMMM yyyy, h:mm aa", smsTime).toString();
        }
    }


    public static String intToTimeString(Long timestamp, int gmt){


        // extracted from resource the SMSA UFRB
//        var seconds = int % 86400;
//        var minutes = seconds / 60;
//        var r_min = minutes % 60;
//        var hours = (minutes-r_min)/60 + gmt;
//        if (r_min < 10){
//            // imprime minutos com dois digitos
//            var min = "00";
//        }
//        else{
//            var min = r_min.toString();
//        }
//        return hours.toString()+"h"+min;

        String min;
        int seconds = (int) (timestamp % 86400);
        int minutes = seconds / 60;
        int r_min = minutes % 60;
        int hours = ( minutes - r_min)/60 + gmt;
        if (r_min < 10){
            min = "00";
        }
        else{
            min = String.valueOf(r_min);
        }
        return String.valueOf(hours) + "h"+ min;
    }


    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public ArrayList<LocateModel> getAllLocateModel(Context context, MainActivityViewModel vim){
        ArrayList<LocateModel> items = new ArrayList<>();
        List<Localization> all_localization = vim.getmRepository().getLocalCampus();
        if (all_localization.isEmpty()) {
            List<String> area = Arrays.asList(context.getResources().getStringArray(R.array.locate_campus));

            for (int i = 0; i < area.size();i++){
                items.add(new LocateModel(area.get(i)));
            }
        }
        else {

            for (Localization local : all_localization) {
                items.add(new LocateModel(local.getName()));
            }
        }
        return items;
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP, Locale.US).format(new Date());
    }

    public static Long getNegativeLong(int number) {
        return ( - (long) number);
    }
}
