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

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.CardViewViewHolder>{

    private ArrayList<MovieItem> nowPlayData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;


    static final String MOVIE_ITEMS = "MOVIE_ITEMS";
    static final String MOVIE_TITLE = "MOVIE_TITLE";
    static final String MOVIE_POSTER = "MOVIE_POSTER";
    static final String MOVIE_DESCRIPTION = "MOVIE_DESCRIPTION";
    static final String MOVIE_DATE = "MOVIE_DATE";
    static final String MOVIE_RATE = "MOVIE_RATE";





    public NowPlayingAdapter(final Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItem> items){
        nowPlayData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item){
        nowPlayData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        nowPlayData.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<MovieItem>  items) {
        nowPlayData.clear();
        nowPlayData = items;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<MovieItem>  items) {
        nowPlayData.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }




    public MovieItem getItem(int position){
        return nowPlayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }





    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.fragment_now_playing, parent, false
                )
        );
    }


    @Override
    public void onBindViewHolder(CardViewViewHolder holder, final int position) {
        Glide.with(context).load(nowPlayData.get(position).getPosterUrl())
                .into(holder.imgPosterNow);
        holder.tvTitleNow.setText(nowPlayData.get(position).getTitle());
        holder.tvDescNow.setText(nowPlayData.get(position).getDescription());
        holder.tvDateNow.setText(nowPlayData.get(position).getReleaseDate());
        holder.btnDetailNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString(MOVIE_TITLE, nowPlayData.get(position).getTitle());
                extras.putString(MOVIE_POSTER, nowPlayData.get(position).getPosterUrl());
                extras.putString(MOVIE_DESCRIPTION, nowPlayData.get(position).getDescription());
                extras.putString(MOVIE_DATE, nowPlayData.get(position).getReleaseDate());
                extras.putString(MOVIE_RATE, nowPlayData.get(position).getMovieRate());
                intent.putExtra(MOVIE_ITEMS, extras);
                context.startActivity(intent);
            }
        });

        holder.btnShareNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (context.getString(R.string.label_share))+ " " + nowPlayData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (nowPlayData == null){
            return 0;
        }
        return nowPlayData.size();
    }




    public class CardViewViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_item_photo_now_play)
        ImageView imgPosterNow;

        @BindView(R.id.tv_title_now_play)
        TextView tvTitleNow;

        @BindView(R.id.tv_desc_now_play)
        TextView tvDescNow;

        @BindView(R.id.tv_rea_date_now_play)
        TextView tvDateNow;

        @BindView(R.id.btn_set_detail_now_play)
        Button btnDetailNow;

        @BindView(R.id.btn_set_share_now_play)
        Button  btnShareNow;



        public CardViewViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    }
