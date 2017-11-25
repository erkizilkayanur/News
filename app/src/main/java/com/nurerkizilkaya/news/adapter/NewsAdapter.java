package com.nurerkizilkaya.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurerkizilkaya.news.R;
import com.nurerkizilkaya.news.model.Haber;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by a s u s on 25.11.2017.
 */
public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    public List<Haber> data;
    public Context context;
    public NewsAdapter(Context context,List<Haber> data)
    {
        this.context = context;
        this.data=data;
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_news_list, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Haber n=data.get(position);
        holder.titleId.setText(n.getTitle().toString());
        holder.dateId.setText(n.getDate().toString());

        Picasso.with(context).load(n.getImage().toString()).into(holder.ImageId);
        holder.descriptionId.setText(n.getSpot().toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.titleId)
        TextView titleId;
        @Bind(R.id.dateId)
        TextView dateId;
        @Bind(R.id.ImageId)
        ImageView ImageId;
        @Bind(R.id.descriptionId)
        TextView descriptionId;
        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
