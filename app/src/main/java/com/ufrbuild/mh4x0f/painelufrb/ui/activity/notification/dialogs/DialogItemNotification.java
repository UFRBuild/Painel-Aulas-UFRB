package com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseDialogFragment;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.HashMap;

public class DialogItemNotification extends BaseDialogFragment<DialogItemNotification.OnDialogFragmentClickListener> {


    private TextView mTextView_title_message;
    private TextView mTextView_content_message;
    private TextView mTextView_date_message;
    private ImageView mImageClose;

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(DialogItemNotification dialog);
        public void onCancelClicked(DialogItemNotification dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_item_notification, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mImageClose = view.findViewById(R.id.img_closeDialogNotification);
        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mTextView_title_message = view.findViewById(R.id.tv_title_notification);
        mTextView_content_message = view.findViewById(R.id.tv_content_notification);
        mTextView_date_message = view.findViewById(R.id.tv_date_notification);

        mTextView_title_message.setText(getArguments().getString("title_message"));
        mTextView_content_message.setText(getArguments().getString("content_message"));
        mTextView_date_message.setText(CommonUtils.getFormattedDate(
                Long.valueOf(getArguments().getString("date_message"))));
        return view;
    }

    // Create an instance of the Dialog with the input
    public static DialogItemNotification newInstance(HashMap<String, String> data) {
        DialogItemNotification frag = new DialogItemNotification();
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
