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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.dialogs;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.HomeViewModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.adapters.WeekAdapter;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.EnumDayWeek;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.SemanaEnum;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.Week;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseDialogFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseFragment;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

// https://stackoverflow.com/questions/23408756/create-a-general-class-for-custom-dialog-in-java-android/23408864

public class DialogItemClassRoom extends
        BaseDialogFragment<DialogItemClassRoom.OnDialogFragmentClickListener> {


    private static final String TAG = "DialogItemClassRoom";
    private TextView mTextView_matter;
    private TextView mTextView_prof;
    private TextView mTextView_duration;
    private TextView mTextView_status;
    private TextView mTextView_start_timer;
    private TextView mTextView_classroom;
    private Switch mSwitchStatusDb;
    private ImageView mImageClose;
    private Discipline mSelectedDiscipline;
    private HomeViewModel viewModel;
    private MutableLiveData<Discipline> mDisciplineFetch;
    private Boolean status_discipline_db;


    public DialogItemClassRoom(){
        mDisciplineFetch = new MutableLiveData<>();
    }

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(DialogItemClassRoom dialog);
        public void onCancelClicked(DialogItemClassRoom dialog);
    }


    public void setMDisciplineFetch(Discipline discipline) {
        this.mDisciplineFetch.postValue(discipline);
    }

    public MutableLiveData<Discipline> getmDisciplineFetch() {
        return mDisciplineFetch;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.setIsLoading(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_item_class_room, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        viewModel = (HomeViewModel) MainActivity.getInstance().getmActiveFragment().getViewModel();

        mImageClose = view.findViewById(R.id.img_closeDialog);
        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mSelectedDiscipline = getArguments().getParcelable("selected_discipline");
        status_discipline_db = getArguments().getBoolean("status_db");

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        mSelectedDiscipline.setDay_week(dayOfWeek);

        getArguments().clear();

        mSwitchStatusDb = view.findViewById(R.id.switch_status_db);

        mSwitchStatusDb.setChecked(status_discipline_db);


        mSwitchStatusDb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    viewModel.getmRepository().insertData(mSelectedDiscipline);
                }
                else{
                    viewModel.getmRepository().deleteDisciplineByID(mSelectedDiscipline);
                }
            }
        });


        mTextView_matter = view.findViewById(R.id.tv_matter);
        mTextView_prof = view.findViewById(R.id.tv_prof);
        mTextView_duration = view.findViewById(R.id.tv_duration);
        mTextView_status = view.findViewById(R.id.tv_status);
        mTextView_start_timer = view.findViewById(R.id.tv_start_timer);
        mTextView_classroom = view.findViewById(R.id.tv_classrom);


        mTextView_matter.setText(mSelectedDiscipline.getName());
        mTextView_prof.setText(mSelectedDiscipline.getDescription());
        mTextView_duration.setText(CommonUtils.intToTimeString(mSelectedDiscipline.getDuration(), 0));
        if (String.valueOf(mSelectedDiscipline.getStatus()).equalsIgnoreCase("2")){
            mTextView_status.setText("Cancelado");
            mTextView_status.setTextColor(MainActivity.getInstance().
                    getResources()
                    .getColor(R.color.colorStatusClassRoom_cancel));
        }
        else if (String.valueOf(mSelectedDiscipline.getStatus()).equalsIgnoreCase("1")){
            mTextView_status.setText("Confirmado");
            mTextView_status.setTextColor(MainActivity.getInstance().
                    getResources()
                    .getColor(R.color.colorStatusClassRoom_confirmed));
        }


        mTextView_classroom.setText(String.valueOf(mSelectedDiscipline.getRoom_name()));
        mTextView_start_timer.setText(CommonUtils.intToTimeString(mSelectedDiscipline.getStart_time(), -3));
        return view;
    }

    // Create an instance of the Dialog with the input
    public static DialogItemClassRoom newInstance(Discipline data, Boolean status) {
        DialogItemClassRoom frag = new DialogItemClassRoom();
        Bundle args = new Bundle();
//        for (String key : data.keySet()) {
//            args.putString(key, data.get(key));
//        }
        Log.i(TAG, "newInstance: " + status);
        args.putBoolean("status_db", status);
        args.putParcelable("selected_discipline", data);
//        args.putString("title", title);
//        args.putString("msg", message);
        frag.setArguments(args);
        return frag;
    }
}
