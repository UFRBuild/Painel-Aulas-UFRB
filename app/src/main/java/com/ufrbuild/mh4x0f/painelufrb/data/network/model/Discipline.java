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

package com.ufrbuild.mh4x0f.painelufrb.data.network.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity(tableName = "DisciplineDetails")
public class Discipline implements Parcelable, Comparable<Discipline> {


    private static final Pattern pHours = Pattern.compile("\\d+");

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdTable")
    private int idtable;

    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "Id")
    private String id;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "Name")
    private String name;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "Description")
    private String description;

    @Expose
    @SerializedName("room_name")
    @ColumnInfo(name = "RoomName")
    private String room_name;

    @Expose
    @SerializedName("start_time")
    @ColumnInfo(name = "StartTime")
    private long start_time;

    @Expose
    @SerializedName("duration")
    @ColumnInfo(name = "Duration")
    private long duration;

    @Expose
    @SerializedName("status")
    @ColumnInfo(name = "Status")
    private int status;

    @ColumnInfo(name = "DayOfWeek")
    private int day_week;

    @ColumnInfo(name = "PavilionName")
    private String pavilionName;

    @Ignore
    private ArrayList<Integer> weeksdays;

    public Discipline(String id,String name, String description,
                      String room_name, long start_time, long duration, int status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.room_name = room_name;
        this.start_time = start_time;
        this.duration = duration;
        this.status = status;
    }

    protected Discipline(Parcel in) {
        id  = in.readString();
        name = in.readString();
        description = in.readString();
        room_name = in.readString();
        duration = in.readLong();
        start_time = in.readLong();
        status = in.readInt();
        day_week = in.readInt();
    }

    public static final Creator<Discipline> CREATOR = new Creator<Discipline>() {
        @Override
        public Discipline createFromParcel(Parcel in) {
            return new Discipline(in);
        }

        @Override
        public Discipline[] newArray(int size) {
            return new Discipline[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public String getPavilionName() {
        return pavilionName;
    }

    public void setPavilionName(String pavilionName) {
        this.pavilionName = pavilionName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public int getStatus() {
        return status;
    }
    public long getDuration() {
        return duration;
    }
    public long getStart_time() {
        return start_time;
    }
    public String getRoom_name() {
        return room_name;
    }

    public void setDescription(String ds) {
        this.description = ds;
    }


    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getIdtable() {
        return idtable;
    }

    public void setIdtable(int idtable) {
        this.idtable = idtable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDay_week() {
        return day_week;
    }

    public void setDay_week(int DAY_OF_WEEK) {
        this.day_week = DAY_OF_WEEK;
    }

    public ArrayList<Integer> getWeeksdays() {
        return weeksdays;
    }

    public void setWeeksdays(ArrayList<Integer> weeksdays) {
        this.weeksdays = weeksdays;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(status);
        parcel.writeLong(start_time);
        parcel.writeLong(duration);
        parcel.writeString(room_name);
        parcel.writeString(description);
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeInt(day_week);
    }

    @Override
    public int compareTo(Discipline another) {
        int hours_class = 0, hours_args = 0;
        Matcher m_class = pHours.matcher(CommonUtils.intToTimeString(this.getStart_time(), -3));
        Matcher m_args = pHours.matcher(CommonUtils.intToTimeString(another.getStart_time(), -3));

        if (m_class.find()) {
            hours_class = Integer.parseInt(m_class.group(0));
        }
        if (m_args.find()) {
            hours_args = Integer.parseInt(m_args.group(0));
        }

        return Integer.compare(hours_class, hours_args);
    }
}
