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

package com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.adapters;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pd.chocobar.ChocoBar;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.NotificationActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.notification.model.Notification;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>  {

    public interface OnNotificationAdapter{
        void onNotificationClicked(Notification notification);
    }

    private List<Notification> mItems;
    private OnNotificationAdapter mListener;
    private DataManager mDataManager;
    private ConstraintLayout myEmptyLayout;

    public NotificationAdapter(OnNotificationAdapter listener, DataManager ref, ConstraintLayout empty) {
        mListener = listener;
        mItems = new ArrayList<>();
        mDataManager = ref;
        myEmptyLayout = empty;
    }

    public void setItems(List<Notification> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = getItem(position);

        holder.setOnClickListener(notification);
        holder.setNofiticationTitle(notification.getTitle());
        holder.setNofiticationContent(notification.getContent());
        holder.setNofiticationData(CommonUtils.getFormattedDate(Long.valueOf(notification.getData_temp())));
        holder.setOnClickBtnRmNotify(notification);

    }

    @Override
    public int getItemCount() {
        myEmptyLayout.setVisibility(mItems.size() > 0 ? View.GONE : View.VISIBLE);
        return mItems.size();
    }

    private Notification getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.notify_title)
        TextView notify_title;
        @BindView(R.id.nofify_cotent)
        TextView notify_content;

        @BindView(R.id.notify_data)
        TextView notify_data;

        @BindView(R.id.btn_rm_notify)
        ImageView btn_rm_message;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setOnClickBtnRmNotify(Notification notify){
            btn_rm_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ChocoBar.builder().setView(itemView).setActionText("Remover")
                            .setActionClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.i("MainActivity", "onClick: " + notify.getTitle());
                                    mDataManager.RemoveNotificationsMessage(notify);
                                    setItems(mDataManager.getNotificationsMessage());

                                }
                            })
                            .setText("Deseja remover está notificação ?")
                            .setTextColor(Color.parseColor("#FFFFFF"))
                            .setDuration(ChocoBar.LENGTH_LONG)
                            .build()
                            .show();
                }
            });
        }

        public void setNofiticationTitle(String title) {
            this.notify_title.setText(title);
        }

        public void setNofiticationContent(String content) {
            this.notify_content.setText(content);
        }

        public void setNofiticationData(String data) {
            this.notify_data.setText(data);
        }


        private void setOnClickListener(Notification notification) {
            itemView.setTag(notification);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onNotificationClicked((Notification) view.getTag());
        }
    }
}

