package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.models;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufrbuild.mh4x0f.painelufrb.R;

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