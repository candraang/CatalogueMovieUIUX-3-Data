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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CardViewViewHolder>{

    private ArrayList<MovieItem> searchData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;


    static final String MOVIE_ITEMS = "MOVIE_ITEMS";
    static final String MOVIE_TITLE = "MOVIE_TITLE";
    static final String MOVIE_POSTER = "MOVIE_POSTER";
    static final String MOVIE_DESCRIPTION = "MOVIE_DESCRIPTION";
    static final String MOVIE_DATE = "MOVIE_DATE";
    static final String MOVIE_RATE = "MOVIE_RATE";





    public SearchAdapter(final Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItem> items){
        searchData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item){
        searchData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        searchData.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<MovieItem>  items) {
        searchData.clear();
        searchData = items;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<MovieItem>  items) {
        searchData.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }




    public MovieItem getItem(int position){
        return searchData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }





    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.activity_search, parent, false
                )
        );
    }


    @Override
    public void onBindViewHolder(CardViewViewHolder holder, final int position) {
        Glide.with(context).load(searchData.get(position).getPosterUrl())
                .into(holder.imgPosterSearch);
        holder.tvTitleSearch.setText(searchData.get(position).getTitle());
        holder.tvDescSearch.setText(searchData.get(position).getDescription());
        holder.tvDateSearch.setText(searchData.get(position).getReleaseDate());
        holder.btnDetailSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString(MOVIE_TITLE, searchData.get(position).getTitle());
                extras.putString(MOVIE_POSTER, searchData.get(position).getPosterUrl());
                extras.putString(MOVIE_DESCRIPTION, searchData.get(position).getDescription());
                extras.putString(MOVIE_DATE, searchData.get(position).getReleaseDate());
                extras.putString(MOVIE_RATE, searchData.get(position).getMovieRate());
                intent.putExtra(MOVIE_ITEMS, extras);
                context.startActivity(intent);
            }
        });

        holder.btnShareSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (context.getString(R.string.label_share))+ " "  + searchData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (searchData == null){
            return 0;
        }
        return searchData.size();
    }




    public class CardViewViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_item_photo_search)
        ImageView imgPosterSearch;

        @BindView(R.id.tv_title_search)
        TextView tvTitleSearch;

        @BindView(R.id.tv_desc_search)
        TextView tvDescSearch;

        @BindView(R.id.tv_rea_date_search)
        TextView tvDateSearch;

        @BindView(R.id.btn_set_detail_search)
        Button btnDetailSearch;

        @BindView(R.id.btn_set_share_search)
        Button  btnShareSearch;



        public CardViewViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    }
