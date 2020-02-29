package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.models.SectionHeaderFavorites;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.models.SectionViewHolder;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeaderFavorites, Discipline, SectionViewHolder, AdapterSectionRecycler.ViewHolder> {

    Context context;

    public AdapterSectionRecycler(Context context, List<SectionHeaderFavorites> sectionItemList) {
        super(context, sectionItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_header_favorites, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public AdapterSectionRecycler.ViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discipline_info, childViewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeaderFavorites section) {
        sectionViewHolder.getName().setText(section.getSectionText());
    }

    @Override
    public void onBindChildViewHolder(ViewHolder holder, int sectionPosition, int position, Discipline discipline) {
        //Discipline discipline = getItem(position);

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

    public void notifyDataChangedList(List<SectionHeaderFavorites> sectionHeaders) {
        super.notifyDataChanged(sectionHeaders);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
            //itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//
//            mListener.onRoomFavoritesClicked((Discipline) view.getTag());
//        }
    }
}