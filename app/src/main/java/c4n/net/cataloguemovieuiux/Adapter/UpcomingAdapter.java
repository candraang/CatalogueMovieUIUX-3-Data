package c4n.net.cataloguemovieuiux.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import c4n.net.cataloguemovieuiux.Activity.DetailActivity;
import c4n.net.cataloguemovieuiux.Model.MovieItem;
import c4n.net.cataloguemovieuiux.R;

/**
 * Created by c4n on 17/11/2017.
 */

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.CardViewViewHolder>{

    private ArrayList<MovieItem> upcomingData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;


    static final String MOVIE_ITEMS = "MOVIE_ITEMS";
    static final String MOVIE_TITLE = "MOVIE_TITLE";
    static final String MOVIE_POSTER = "MOVIE_POSTER";
    static final String MOVIE_DESCRIPTION = "MOVIE_DESCRIPTION";
    static final String MOVIE_DATE = "MOVIE_DATE";
    static final String MOVIE_RATE = "MOVIE_RATE";





    public UpcomingAdapter(final Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItem> items){
        upcomingData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item){
        upcomingData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        upcomingData.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<MovieItem>  items) {
        upcomingData.clear();
        upcomingData = items;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<MovieItem>  items) {
        upcomingData.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }




    public MovieItem getItem(int position){
        return upcomingData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }





    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.fragment_upcoming, parent, false
                )
        );
    }


    @Override
    public void onBindViewHolder(CardViewViewHolder holder, final int position) {
        Glide.with(context).load(upcomingData.get(position).getPosterUrl())
                .into(holder.imgPosterUp);
        holder.tvTitleUp.setText(upcomingData.get(position).getTitle());
        holder.tvDescUp.setText(upcomingData.get(position).getDescription());
        holder.tvDateUp.setText(upcomingData.get(position).getReleaseDate());
        holder.btnDetailUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString(MOVIE_TITLE, upcomingData.get(position).getTitle());
                extras.putString(MOVIE_POSTER, upcomingData.get(position).getPosterUrl());
                extras.putString(MOVIE_DESCRIPTION, upcomingData.get(position).getDescription());
                extras.putString(MOVIE_DATE, upcomingData.get(position).getReleaseDate());
                extras.putString(MOVIE_RATE, upcomingData.get(position).getMovieRate());
                intent.putExtra(MOVIE_ITEMS, extras);
                context.startActivity(intent);
            }
        });

        holder.btnShareUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (context.getString(R.string.label_share))+ " "  + upcomingData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (upcomingData == null){
            return 0;
        }
        return upcomingData.size();
    }




    public class CardViewViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_item_photo_upcoming)
        ImageView imgPosterUp;

        @BindView(R.id.tv_title_upcoming)
        TextView tvTitleUp;

        @BindView(R.id.tv_desc_upcoming)
        TextView tvDescUp;

        @BindView(R.id.tv_rea_date_upcoming)
        TextView tvDateUp;

        @BindView(R.id.btn_set_detail_upcoming)
        Button btnDetailUp;

        @BindView(R.id.btn_set_share_upcoming)
        Button  btnShareUp;



        public CardViewViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    }
