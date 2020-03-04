package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.adapters;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models.Developer;

import java.util.ArrayList;
import java.util.List;

public class DevelopersAdapter extends RecyclerView.Adapter<DevelopersAdapter.ViewHolder>  {

    public interface OnDevelopersAdapter{
        void onDevelopersClicked(Developer discipline);
    }

    private List<Developer> mItems;
    private OnDevelopersAdapter mListener;

    public DevelopersAdapter(OnDevelopersAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<Developer> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_developers_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Developer developer = getItem(position);

        holder.setOnClickListener(developer);
        holder.setNameDeveloper(developer.getName());
        holder.setImage(developer.getImage_link());
        holder.setGithub(developer.getGithub());
        holder.setInstagram(developer.getInstagram());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private Developer getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_developer_name)
        TextView name_developer;
        @BindView(R.id.image_developer)
        AppCompatImageView image;
        @BindView(R.id.tv_developer_github)
        TextView github;
        @BindView(R.id.tv_developer_instagram)
        TextView instagram;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setGithub(String github) {
            this.github.setClickable(true);
            this.github.setMovementMethod(LinkMovementMethod.getInstance());
            String text = String.format("<a href='%s'> Github </a>",github);
            this.github.setText(Html.fromHtml(text));

        }

        public void setInstagram(String instagram) {
            this.instagram.setClickable(true);
            this.instagram.setMovementMethod(LinkMovementMethod.getInstance());
            String text = String.format("<a href='%s'> Instagram </a>",instagram);
            this.instagram.setText(Html.fromHtml(text));
        }


        public void setNameDeveloper(String title) {
            this.name_developer.setText(title);
        }


        public void setImage(String imageUrl) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .apply(options)
                    .into(image);
        }

        private void setOnClickListener(Developer developer) {
            itemView.setTag(developer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onDevelopersClicked((Developer) view.getTag());
        }
    }
}
