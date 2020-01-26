package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.adapters;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.Week;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {


    public interface OnWeekAdapter{
        void onWeekClicked(Week week);
    }

    private List<Week> mItems;
    private OnWeekAdapter mListener;

    public WeekAdapter(OnWeekAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<Week> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Week week = getItem(position);

        holder.setOnClickListener(week);
        holder.setMtitleWeek(week.getDayName());
        if (week.getStatus())
            holder.setLinearLayoutColor(1);
        else
            holder.setLinearLayoutColor(0);
    }

    private void refresh(){
        Log.i("result", "refresh: ");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private Week getItem (int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_title_week) TextView mtitleWeek;
        @BindView(R.id.linear_layout_week)
        ConstraintLayout mlinear_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getMtitleWeek() {
            return mtitleWeek;
        }

        public void setMtitleWeek(String title) {
            this.mtitleWeek.setText(title);
        }

        public void setLinearLayoutColor(int flag){
            if (flag == 1){
                mlinear_layout.setBackgroundColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.accent));
                mtitleWeek.setTextColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.colorPrimary));
            }
            else {
                mlinear_layout.setBackgroundColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.color_status_week_inative));
                mtitleWeek.setTextColor(MainActivity.getInstance().
                        getResources()
                        .getColor(R.color.material_drawer_dark_primary_text));
            }

        }


        private void setOnClickListener(Week week) {
            itemView.setTag(week);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mListener.onWeekClicked((Week) view.getTag());
        }
    }
}
