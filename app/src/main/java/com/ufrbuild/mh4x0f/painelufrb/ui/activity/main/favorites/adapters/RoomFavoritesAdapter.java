package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

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



public class RoomFavoritesAdapter extends RecyclerView.Adapter<RoomFavoritesAdapter.ViewHolder> implements Filterable {

    @Override
    public Filter getFilter() {
        return FilderItems;
    }

    private Filter FilderItems = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Discipline> filteredList =  new ArrayList<>();

            if (charSequence ==null || charSequence.length()  == 0 ){
                filteredList.addAll(mItemsFull);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                if (mItemsFull != null) {
                    for (Discipline item : mItemsFull) {
                        if (item.getName().toLowerCase().contains(filterPattern) || item.getDescription().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
            }

            FilterResults results =  new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (((List)filterResults.values) != null){
                mItems.clear();
                mItems.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        }
    };

    public interface OnRoomAdapterFavorites{
        void onRoomFavoritesClicked(Discipline discipline);
    }

    private List<Discipline> mItems;
    private List<Discipline> mItemsFull;
    private OnRoomAdapterFavorites mListener;

    public RoomFavoritesAdapter(OnRoomAdapterFavorites listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<Discipline> items) {
        mItems = items;
        mItemsFull = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discipline_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Discipline discipline = getItem(position);

        holder.setOnClickListener(discipline);
        holder.setTitle(discipline.getName());

        if (discipline.getStatus() == 1){
            holder.setStatus("Confirmado");
        }
        else if (discipline.getStatus() == 2){
            holder.setStatus("Cancelado");
        }
        else{
            holder.setStatus("Em Aguardo");
        }
        holder.setmViewColorStatus(discipline.getStatus());
        holder.setSala(discipline.getRoom_name());


        // array de linhas da tabela
        // extracted from painel web ufrb
//        var row=[];
//        for(var i=0; i<data.result.length; i++){
//            row[i] ="<tr>"+
//                    "<td>"+data.result[i].name+"</td>"+
//                    "<td>"+data.result[i].description+"</td>"+
//                    "<td>"+data.result[i].room_name+"</td>"+
//                    "<td>"+intToTime(data.result[i].start_time,-3)+"</td>"+
//                    "<td >"+intToTime(data.result[i].duration,0)+"</td>"+
//                    "<td>"+statusToString(data.result[i].status)+


        holder.setStart_time(CommonUtils.intToTimeString(discipline.getStart_time(), -3));
        holder.setDuration(CommonUtils.intToTimeString(discipline.getDuration(), 0));


        //Log.i("result", "onBindViewHolder: " + discipline.intToTimeString(discipline.getStart_time(),-3));


        //holder.setImage(discipline.getImage());
        holder.setDescription(discipline.getDescription());

    }

    private void refresh(){
        Log.i("result", "refresh: ");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private Discipline getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.desc) TextView desc;
        @BindView(R.id.txt_status) TextView status;
        @BindView(R.id.sala) TextView sala;
        @BindView(R.id.txt_duration) TextView duration;
        @BindView(R.id.txt_timer_start) TextView start_time;
        @BindView(R.id.view_color_status) View mViewColorStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDuration(String duration) {
            this.duration.setText(duration);
        }

        public void setStart_time(String start_time) {
            this.start_time.setText(start_time);
        }

        public void setSala(String sala) {
            this.sala.setText(sala);
        }

        public void setmViewColorStatus(int flag){
            if (flag == 1){
                mViewColorStatus.setBackgroundColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_confirmed));
                status.setTextColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_confirmed));
            }
            else if (flag == 2){
                mViewColorStatus.setBackgroundColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_cancel));
                status.setTextColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_cancel));
            }
            else{
                mViewColorStatus.setBackgroundColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_wating));
                status.setTextColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_wating));
            }

        }


        public void setTitle(String title) {
            this.title.setText(title);
        }


        private void setDescription(String description) {
            desc.setText(description);
        }

        public void setStatus(String estado) {
            this.status.setText(estado);
        }


        private void setOnClickListener(Discipline discipline) {
            itemView.setTag(discipline);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mListener.onRoomFavoritesClicked((Discipline) view.getTag());
        }
    }
}
