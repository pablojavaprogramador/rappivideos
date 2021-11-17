package com.rappi.adminsion;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rappi.adminsion.ui.main.PlaceholderFragment;
import com.rappi.adminsion.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        final BottomNavigationView navigationType = findViewById(R.id.navigationType);

        navigationType.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                PlaceholderFragment viewPagerFragment = (PlaceholderFragment) sectionsPagerAdapter.instantiateItem(viewPager, tabs.getSelectedTabPosition());
                if (viewPagerFragment.isAdded() && viewPagerFragment.getNavigationTypePath() != menuItem.getItemId()) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_movies:
                            viewPagerFragment.setNavigationTypePath("movie");
                            break;
                        case R.id.nav_series:
                            viewPagerFragment.setNavigationTypePath("tv");
                            break;
                        case R.id.acerca:
                            viewPagerFragment.setNavigationTypePath("Acerca");
                            break;

                    }
                    toolbar.collapseActionView();
                    viewPagerFragment.cancelAllRequest();
                    if (viewPager.getAdapter() != null) {
                        viewPager.getAdapter().notifyDataSetChanged();
                        return true;
                    }
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                toolbar.collapseActionView();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.app_seach_bar);
        SearchView action_search = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            assert searchManager != null;
            action_search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            action_search.setQueryHint(getString(R.string.search));
            action_search.setIconifiedByDefault(true);
            action_search.setQueryRefinementEnabled(true);

            action_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equalsIgnoreCase("")) {
                        for (int i = 0; i < tabs.getTabCount(); i++) {
                            PlaceholderFragment viewPagerFragment = (PlaceholderFragment) sectionsPagerAdapter.instantiateItem(viewPager, i);
                            if (viewPagerFragment.isAdded()) {
                                viewPagerFragment.filterListview("");
                            }
                        }
                    } else {
                        PlaceholderFragment viewPagerFragment = (PlaceholderFragment) sectionsPagerAdapter.instantiateItem(viewPager, tabs.getSelectedTabPosition());
                        if (viewPagerFragment.isAdded()) {
                            viewPagerFragment.filterListview(s);
                        }
                    }
                    return false;
                }
            });
        return super.onCreateOptionsMenu(menu);
    }
}