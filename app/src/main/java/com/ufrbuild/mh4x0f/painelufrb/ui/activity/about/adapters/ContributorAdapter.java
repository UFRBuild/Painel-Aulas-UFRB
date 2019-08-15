package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
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
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models.Contributor;
import java.util.ArrayList;
import java.util.List;


public class ContributorAdapter extends RecyclerView.Adapter<ContributorAdapter.ViewHolder>  {

    public interface OnContributorAdapter{
        void onContributorClicked(Contributor contributor);
    }

    private List<Contributor> mItems;
    private OnContributorAdapter mListener;

    public ContributorAdapter(OnContributorAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<Contributor> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contributor_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contributor contributor = getItem(position);

        holder.setOnClickListener(contributor);
        holder.setNameContributor(contributor.getName());
        holder.setImage(contributor.getImage_link());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private Contributor getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_contributor_name)
        TextView name_contributor;
        @BindView(R.id.image_contributors)
        AppCompatImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setNameContributor(String title) {
            this.name_contributor.setText(title);
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


        private void setOnClickListener(Contributor contributor) {
            itemView.setTag(contributor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onContributorClicked((Contributor) view.getTag());
        }
    }
}

