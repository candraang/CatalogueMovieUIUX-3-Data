package c4n.net.cataloguemovieuiux.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import c4n.net.cataloguemovieuiux.Adapter.UpcomingAdapter;

import c4n.net.cataloguemovieuiux.Loaders.UpcomingAsyncTaskLoader;
import c4n.net.cataloguemovieuiux.Model.MovieItem;
import c4n.net.cataloguemovieuiux.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    private Context context;
    private Unbinder unbinder;

    private ArrayList<MovieItem> upcomingData;
    private UpcomingAdapter adapter;
    private LinearLayout linearLayout;



    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        context = view.getContext();

        unbinder = ButterKnife.bind(this, view);
        adapter = new UpcomingAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        return new UpcomingAsyncTaskLoader(getContext(), upcomingData);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> upcomingData) {
        adapter.setData(upcomingData);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }


}

