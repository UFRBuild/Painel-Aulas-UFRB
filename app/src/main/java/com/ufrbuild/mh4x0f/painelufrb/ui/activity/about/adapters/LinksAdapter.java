package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models.Links;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import java.util.ArrayList;
import java.util.List;


public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ViewHolder>  {

    public interface OnLinksAdapter{
        void onLinksClicked(Links link);
    }

    private List<Links> mItems;
    private OnLinksAdapter mListener;

    public LinksAdapter(OnLinksAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<Links> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_links_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Links link = getItem(position);

        holder.setOnClickListener(link);
        holder.setLinkTitle(link.getTitle());
        //holder.setLinkImage(link.getImg_path());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private Links getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.link_title)
        TextView link_title;
        @BindView(R.id.img_link)
        ImageView link_image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setLinkTitle(String title) {
            this.link_title.setText(title);
        }

        public void setLinkImage(String image_path ){
            this.link_image.setImageDrawable(MainActivity.getInstance().getResources().getDrawable(R.drawable.ic_close_black_24dp));
        }


        private void setOnClickListener(Links link) {
            itemView.setTag(link);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onLinksClicked((Links) view.getTag());
        }
    }
}