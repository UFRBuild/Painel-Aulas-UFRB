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

package com.ufrbuild.mh4x0f.painelufrb.data;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ufrbuild.mh4x0f.painelufrb.PainelUFRBApp;
import com.ufrbuild.mh4x0f.painelufrb.data.db.database.LogDatabase;
import com.preference.PowerPreference;
import com.preference.Preference;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.model.Notification;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    public Context context;
    Gson mGconverter;

    @Inject
    public DataManager(Context context,Gson gson) {
        this.context = context;
        this.mGconverter = gson;
    }

    public boolean isFirstTimeLaunch(){
        return getPrefs().getBoolean("FirstTimeLaunch",false);
    }

    public Preference getPrefs() {
        return PowerPreference.getDefaultFile();
    }

    public LogDatabase getLogDatabse() {
        return LogDatabase.getInstance(PainelUFRBApp.getInstance());
    }

    public void putNotificationMessage(Notification notify){
        // add notification into data preference
        ArrayList<Notification> list_message = new ArrayList<>();
        if (getNotificationsMessage() != null){
            list_message = getNotificationsMessage();
        }
        list_message.add(notify);
        String json_notifications = mGconverter.toJson(list_message);
        getPrefs().putString("notifications",json_notifications);
    }

    public ArrayList<Notification> getNotificationsMessage(){
        // get all notification from android preference
        ArrayList<Notification> list_notifications;
        String json = getPrefs().getString("notifications");
        Type type =  new TypeToken<List<Notification>>() {}.getType();
        list_notifications = mGconverter.fromJson(json, type);
        return list_notifications;
    }

    public void RemoveNotificationsMessage(Notification notification){
        // remove one notification from data preference
        ArrayList<Notification> list_notifications = getNotificationsMessage();

        for(Notification p : list_notifications) {
            if (p.getData_temp().equals(notification.getData_temp())){
                list_notifications.remove(p);
                break;
            }
        }
        String json_notifications = mGconverter.toJson(list_notifications);
        getPrefs().putString("notifications",json_notifications);
    }

}
