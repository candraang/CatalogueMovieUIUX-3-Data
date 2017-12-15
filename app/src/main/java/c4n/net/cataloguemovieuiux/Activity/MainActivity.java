package c4n.net.cataloguemovieuiux.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import c4n.net.cataloguemovieuiux.Fragment.NowPlayingFragment;

import c4n.net.cataloguemovieuiux.Fragment.UpcomingFragment;
import c4n.net.cataloguemovieuiux.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    CircleImageView profileCircleImageView;

    String profileImageUrl = "https://scontent.fmdc1-1.fna.fbcdn.net/v/t1.0-9/1926885_1375127179438210_8194078519852040118_n.jpg?oh=895c09a9ffb2efbb90bc73e1faf24af5&oe=5A933B48";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        profileCircleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

        Glide.with(MainActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);
        navigationView.setNavigationItemSelectedListener(this);

        setupDrawer();
        setupTab();

        selectNav(0);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        selectNav(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_now_playing) {
            selectTab(0);

        } else if (id == R.id.nav_upcoming) {
            selectTab(1);

        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, (this.getString(R.string.label_share)), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, (this.getString(R.string.send)), Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupTab() {
        viewPager.setAdapter(new ATabPager(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setText(R.string.label_now_playing);
        tabs.getTabAt(1).setText(R.string.label_upcoming);

        tabs.setOnTabSelectedListener(this);
    }

    private void selectNav(int navNumber) {
        navigationView.getMenu().getItem(navNumber).setChecked(true);
    }

    private void selectTab(int tabNumber) {
        tabs.getTabAt(tabNumber).select();
    }


    class ATabPager extends FragmentPagerAdapter {
        private static final int NUM_ITEMS = 2;
        public ATabPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NowPlayingFragment();

                case 1:
                    return new UpcomingFragment();



                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
