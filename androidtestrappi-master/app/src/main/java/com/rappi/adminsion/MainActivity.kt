package com.rappi.adminsion

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.rappi.adminsion.databinding.ActivityMainBinding
import com.rappi.adminsion.ui.main.PlaceholderFragment
import com.rappi.adminsion.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {
    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private var viewPager: ViewPager? = null
    private var tabs: TabLayout? = null
    private var toolbar: Toolbar? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.getRoot())
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager?.setAdapter(sectionsPagerAdapter)
        viewPager?.setOffscreenPageLimit(2)
        tabs = findViewById(R.id.tabs)
        tabs?.setupWithViewPager(viewPager)
        val navigationType = findViewById<BottomNavigationView>(R.id.navigationType)
        navigationType.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            val viewPagerFragment = tabs?.getSelectedTabPosition()?.let {
                sectionsPagerAdapter!!.instantiateItem(
                    viewPager!!,
                    it
                )
            } as PlaceholderFragment
            if (viewPagerFragment.isAdded && viewPagerFragment.navigationTypePath != menuItem.itemId) {
                when (menuItem.itemId) {
                    R.id.nav_movies -> viewPagerFragment.setNavigationTypePath("movie")
                    R.id.nav_series -> viewPagerFragment.setNavigationTypePath("tv")
                    R.id.acerca -> viewPagerFragment.setNavigationTypePath("Acerca")
                }
                toolbar?.collapseActionView()
                viewPagerFragment.cancelAllRequest()
                if (viewPager?.getAdapter() != null) {
                    viewPager?.getAdapter()!!.notifyDataSetChanged()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        viewPager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                toolbar?.collapseActionView()
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val searchItem = menu.findItem(R.id.app_seach_bar)
        val action_search = searchItem.actionView as SearchView
        val searchManager = (getSystemService(SEARCH_SERVICE) as SearchManager)
        action_search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        action_search.queryHint = getString(R.string.search)
        action_search.setIconifiedByDefault(true)
        action_search.isQueryRefinementEnabled = true
        action_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (s.equals("", ignoreCase = true)) {
                    for (i in 0 until tabs!!.tabCount) {
                        val viewPagerFragment = sectionsPagerAdapter!!.instantiateItem(
                            viewPager!!, i
                        ) as PlaceholderFragment
                        if (viewPagerFragment.isAdded) {
                            viewPagerFragment.filterListview("")
                        }
                    }
                } else {
                    val viewPagerFragment = sectionsPagerAdapter!!.instantiateItem(
                        viewPager!!, tabs!!.selectedTabPosition
                    ) as PlaceholderFragment
                    if (viewPagerFragment.isAdded) {
                        viewPagerFragment.filterListview(s)
                    }
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}