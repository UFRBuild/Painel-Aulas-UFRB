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

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseDialogFragment;
import java.util.HashMap;

// https://stackoverflow.com/questions/23408756/create-a-general-class-for-custom-dialog-in-java-android/23408864

public class DialogItemClassRoom extends BaseDialogFragment<DialogItemClassRoom.OnDialogFragmentClickListener> {


    private TextView mTextView_matter;
    private TextView mTextView_prof;
    private TextView mTextView_duration;
    private TextView mTextView_status;
    private TextView mTextView_start_timer;
    private TextView mTextView_classroom;
    private ImageView mImageClose;
    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(DialogItemClassRoom dialog);
        public void onCancelClicked(DialogItemClassRoom dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_item_class_room, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mImageClose = view.findViewById(R.id.img_closeDialog);
        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mTextView_matter = view.findViewById(R.id.tv_matter);
        mTextView_prof = view.findViewById(R.id.tv_prof);
        mTextView_duration = view.findViewById(R.id.tv_duration);
        mTextView_status = view.findViewById(R.id.tv_status);
        mTextView_start_timer = view.findViewById(R.id.tv_start_timer);
        mTextView_classroom = view.findViewById(R.id.tv_classrom);

        mTextView_matter.setText(getArguments().getString("matter"));
        mTextView_prof.setText(getArguments().getString("professor"));
        mTextView_duration.setText(getArguments().getString("duration"));
        if (getArguments().getString("status").equalsIgnoreCase("2")){
            mTextView_status.setText("Cancelado");
            mTextView_status.setTextColor(MainActivity.getInstance().
                    getResources()
                    .getColor(R.color.colorStatusClassRoom_cancel));
        }
        else if (getArguments().getString("status").equalsIgnoreCase("1")){
            mTextView_status.setText("Confirmado");
            mTextView_status.setTextColor(MainActivity.getInstance().
                    getResources()
                    .getColor(R.color.colorStatusClassRoom_confirmed));
        }

        mTextView_classroom.setText(getArguments().getString("class_room"));
        mTextView_start_timer.setText(getArguments().getString("start_timer"));
        return view;
    }

    // Create an instance of the Dialog with the input
    public static DialogItemClassRoom newInstance(HashMap<String, String> data) {
        DialogItemClassRoom frag = new DialogItemClassRoom();
        Bundle args = new Bundle();
        for (String key : data.keySet()) {
            args.putString(key, data.get(key));
        }
//        args.putString("title", title);
//        args.putString("msg", message);
        frag.setArguments(args);
        return frag;
    }
}
