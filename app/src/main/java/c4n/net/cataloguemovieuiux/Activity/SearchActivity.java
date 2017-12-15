package c4n.net.cataloguemovieuiux.Activity;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import c4n.net.cataloguemovieuiux.Adapter.SearchAdapter;
import c4n.net.cataloguemovieuiux.Loaders.SearchAsyncTaskLoader;
import c4n.net.cataloguemovieuiux.Model.MovieItem;
import c4n.net.cataloguemovieuiux.R;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    @BindView(R.id.search_recycler_view)
    RecyclerView rv_search;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    private ArrayList<MovieItem> searchData;

    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_recyclerview);

        ButterKnife.bind(this);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new SearchAdapter(this);


        rv_search.setLayoutManager(new LinearLayoutManager(this));
        rv_search.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        String query = "";
        if (args != null) {
            query = args.getString(EXTRAS_MOVIE);
        }
        return new SearchAsyncTaskLoader(this, query, searchData);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> searchData) {
        adapter.setData(searchData);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_search, menu);

        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.id_search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_MOVIE, query);
                getLoaderManager().restartLoader(0, bundle, SearchActivity.this);
                return true;
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
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
