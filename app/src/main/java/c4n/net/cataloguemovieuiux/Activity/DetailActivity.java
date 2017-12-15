package c4n.net.cataloguemovieuiux.Activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import c4n.net.cataloguemovieuiux.R;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.detail_poster)
    ImageView imgPosterDetail;

    @BindView(R.id.detail_title_1)
    TextView detailTitle;

    @BindView(R.id.detail_description)
    TextView detailDesc;

    @BindView(R.id.detail_release_date)
    TextView detailDate;

    @BindView(R.id.detail_movie_rate)
    TextView detailRate;

    private static final String MOVIE_ITEMS = "MOVIE_ITEMS";
    private static final String MOVIE_TITLE = "MOVIE_TITLE";
    private static final String MOVIE_POSTER = "MOVIE_POSTER";
    private static final String MOVIE_DESCRIPTION = "MOVIE_DESCRIPTION";
    private static final String MOVIE_DATE = "MOVIE_DATE";
    private static final String MOVIE_RATE = "MOVIE_RATE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen



        Bundle extras = getIntent().getBundleExtra(MOVIE_ITEMS);
        Glide.with(this).load(extras.getString(MOVIE_POSTER)).into(imgPosterDetail);
        detailTitle.setText(extras.getString(MOVIE_TITLE));;
        detailDesc.setText(extras.getString(MOVIE_DESCRIPTION));;
        detailDate.setText(extras.getString(MOVIE_DATE));;
        detailRate.setText(extras.getString(MOVIE_RATE));;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
