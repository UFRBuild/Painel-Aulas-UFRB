package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.adapters;
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
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.SectionHeader;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleSectionAdapter extends SectionRecyclerViewAdapter<SectionHeader, Discipline, ScheduleSectionAdapter.SectionViewHolder, ScheduleSectionAdapter.ViewHolder> {

    Context context;

    public ScheduleSectionAdapter(Context context, List<SectionHeader> sectionItemList) {
        super(context, sectionItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_header_favorites, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ScheduleSectionAdapter.ViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule_discipline_info, childViewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader section) {
        sectionViewHolder.getName().setText(section.getSectionText());
    }



    @Override
    public void onBindChildViewHolder(ViewHolder holder, int sectionPosition, int position, Discipline discipline) {
        //Discipline discipline = getItem(position);

        holder.setOnClickListener(discipline);
        holder.setTitle(discipline.getName());

        holder.setmViewColorStatus(discipline.getStatus());
        holder.setSala(discipline.getRoom_name());

        holder.setStart_time(CommonUtils.intToTimeString(discipline.getStart_time(), -3));
        holder.setDuration(CommonUtils.intToTimeString(discipline.getDuration(), 0));

        holder.setMonitoria(discipline.getName());

        //holder.setImage(discipline.getImage());
        holder.setPavilion(discipline.getPavilionName());
    }


    public class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        public SectionViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.section_item);
        }

        public TextView getName() {
            return name;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.pavilion) TextView pavilion;
        @BindView(R.id.txtmonitoria) TextView monitoria;
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
            }
            else if (flag == 2){
                mViewColorStatus.setBackgroundColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_cancel));
            }
            else{
                mViewColorStatus.setBackgroundColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorStatusClassRoom_wating));
            }

        }

        public TextView getMonitoria() {
            return monitoria;
        }

        public void setMonitoria(String name) {
            if (name.toLowerCase().indexOf("mv") != -1|| name.toLowerCase().indexOf("monitoria") != -1){
                monitoria.setVisibility(View.VISIBLE);
            }else{
                monitoria.setVisibility(View.INVISIBLE);
            }
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }


        private void setPavilion(String name) {
            pavilion.setText(name);
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