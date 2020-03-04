package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models.License;

import java.util.ArrayList;
import java.util.List;


public class LicenseAdapter extends RecyclerView.Adapter<LicenseAdapter.ViewHolder>  {

    public interface OnLicenseAdapter{
        void onLicenseClicked(License license);
    }

    private List<License> mItems;
    private OnLicenseAdapter mListener;

    public LicenseAdapter(OnLicenseAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<License> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_license_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        License license = getItem(position);

        holder.setOnClickListener(license);
        holder.setLicenseTitle(license.getName());
        holder.setDescription(license.getDescription());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private License getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.license_title)
        TextView license_title;
        @BindView(R.id.license_description)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setLicenseTitle(String title) {
            this.license_title.setText(title);
        }

        public void setDescription(String title) {
            this.description.setText(title);
        }


        private void setOnClickListener(License license) {
            itemView.setTag(license);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onLicenseClicked((License) view.getTag());
        }
    }
}