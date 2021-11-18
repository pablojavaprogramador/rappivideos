package com.rappi.adminsion.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.rappi.adminsion.ActivityDetallesPelicula
import com.rappi.adminsion.ActivityDetallesTv
import com.rappi.adminsion.R
import com.rappi.adminsion.data.remote.ApiRequest
import com.rappi.adminsion.data.remote.pelicula.MovieResponse
import com.rappi.adminsion.data.remote.serie.TvResponse
import com.rappi.adminsion.ui.main.PlaceholderFragment.CallbackResponse
import com.rappi.adminsion.ui.main.PlaceholderFragment.StateType
import com.rappi.adminsion.ui.main.pelicula.MovieItemAdapter
import com.rappi.adminsion.ui.main.series.TvItemAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceholderFragment : Fragment() {
    private val callbackResponse = CallbackResponse()
    private var categoryPath = "popular"
    private var movieItemAdapter: MovieItemAdapter? = null
    private var tvItemAdapter: TvItemAdapter? = null
    private var footerView: View? = null
    private var frameLayout: FrameLayout? = null
    private var retry_Layout: ConstraintLayout? = null
    private var textView_not_Info: TextView? = null
    private var listView: ListView? = null

    private enum class StateType {
        GONE, LOADING, RETRY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        var index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        categoryPath = when (index) {
            1 -> "popular"
            2 -> "top_rated"
            else -> "upcoming"
        }
        if (context != null) {
            apiRequest = ApiRequest(
                context
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        textView_not_Info = root.findViewById(R.id.textView_not_Info)
        listView = root.findViewById(R.id.listmovies)
        retry_Layout = root.findViewById(R.id.retry_Layout)
        movieItemAdapter = null
        tvItemAdapter = null
        footerView = inflater.inflate(R.layout.footer_layout, container, false)
        if (context != null) {
            frameLayout = FrameLayout(requireContext())
            listView?.addFooterView(frameLayout)
        }
        footerView?.findViewById<View>(R.id.button_retry)?.setOnClickListener { loadMore() }
        retry_Layout?.findViewById<View>(R.id.button_retry)?.setOnClickListener {
            getDataApi(
                1
            )
        }
        listView?.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val activityDetails: Intent
            val b = Bundle()
            if (Companion.navigationTypePath.equals("movie", ignoreCase = true)) {
                activityDetails = Intent(activity, ActivityDetallesPelicula::class.java)
                b.putInt("MovieId", movieItemAdapter!!.getItem(position).id)
            } else {
                activityDetails = Intent(activity, ActivityDetallesTv::class.java)
                b.putInt("TvId", tvItemAdapter!!.getItem(position).id)
            }
            activityDetails.putExtras(b)
            startActivity(activityDetails)
        })
        listView?.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (visibleItemCount != 0 && firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    if (frameLayout!!.childCount == 0) {
                        loadMore()
                    }
                }
            }
        })
        getDataApi(1)
        return root
    }

    fun filterListview(s: String?) {
        if (Companion.navigationTypePath.equals("movie", ignoreCase = true)) {
            if (movieItemAdapter != null) movieItemAdapter!!.filter.filter(s)
        } else {
            if (tvItemAdapter != null) tvItemAdapter!!.filter.filter(s)
        }
    }

    fun setNavigationTypePath(value: String) {
        Companion.navigationTypePath = value
    }

    val navigationTypePath: Int
        get() = if (Companion.navigationTypePath.equals(
                "movie",
                ignoreCase = true
            )
        ) R.id.nav_movies else R.id.nav_series

    fun cancelAllRequest() {
        apiRequest!!.cancelAll()
    }

    private fun loadMore() {
        if (Companion.navigationTypePath.equals("movie", ignoreCase = true)) {
            if (movieItemAdapter != null && movieItemAdapter!!.page < movieItemAdapter!!.totalPages && !movieItemAdapter!!.isFiltered) getDataApi(
                movieItemAdapter!!.page + 1
            )
        } else {
            if (tvItemAdapter != null && tvItemAdapter!!.page < tvItemAdapter!!.totalPages && !tvItemAdapter!!.isFiltered) getDataApi(
                tvItemAdapter!!.page + 1
            )
        }
    }

    private fun setStatePost(type: StateType) {
        Log.d(
            TAG,
            "setStatePost: $type"
        )
        if (type == StateType.GONE) {
            frameLayout!!.removeAllViews()
            retry_Layout!!.visibility = View.GONE
        } else {
            if (listView!!.adapter != null) {
                if (type == StateType.RETRY) {
                    footerView!!.findViewById<View>(R.id.progressBar).visibility =
                        View.GONE
                    footerView!!.findViewById<View>(R.id.button_retry).visibility =
                        View.VISIBLE
                    footerView!!.findViewById<View>(R.id.footer_label).visibility =
                        View.VISIBLE
                } else {
                    footerView!!.findViewById<View>(R.id.progressBar).visibility =
                        View.VISIBLE
                    footerView!!.findViewById<View>(R.id.button_retry).visibility =
                        View.GONE
                    footerView!!.findViewById<View>(R.id.footer_label).visibility =
                        View.GONE
                }
                frameLayout!!.removeAllViews()
                frameLayout!!.addView(footerView)
            } else {
                if (type == StateType.RETRY) {
                    retry_Layout!!.findViewById<View>(R.id.progressBar).visibility =
                        View.GONE
                    retry_Layout!!.findViewById<View>(R.id.button_retry).visibility =
                        View.VISIBLE
                    retry_Layout!!.findViewById<View>(R.id.footer_label).visibility =
                        View.VISIBLE
                } else {
                    retry_Layout!!.findViewById<View>(R.id.progressBar).visibility =
                        View.VISIBLE
                    retry_Layout!!.findViewById<View>(R.id.button_retry).visibility =
                        View.GONE
                    retry_Layout!!.findViewById<View>(R.id.footer_label).visibility =
                        View.GONE
                }
                retry_Layout!!.visibility = View.VISIBLE
            }
        }
    }

    private fun getDataApi(page: Int) {
        Log.d(TAG, "getPosts")
        if (apiRequest != null) {
            setStatePost(StateType.LOADING)
            textView_not_Info!!.visibility = View.GONE
            apiRequest!!.getData(callbackResponse, Companion.navigationTypePath, categoryPath, page)
        }
    }

    private inner class CallbackResponse : Callback<Any?> {
        override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
            Log.d(TAG, "onResponse")
            if (response.body() is MovieResponse) {
                val data = response.body() as MovieResponse?
                if (Companion.navigationTypePath.equals("movie", ignoreCase = true)) {
                    if (movieItemAdapter != null) {
                        movieItemAdapter!!.filter.filter("")
                        movieItemAdapter!!.page = data!!.getPage()
                        movieItemAdapter!!.addItems(data.getResults())
                        movieItemAdapter!!.notifyDataSetChanged()
                    } else {
                        movieItemAdapter =
                            MovieItemAdapter(activity, data!!.getResults(), data.getTotal_pages())
                        listView!!.adapter = movieItemAdapter
                    }
                    setStatePost(StateType.GONE)
                }
            } else if (response.body() is TvResponse) {
                val data = response.body() as TvResponse?
                if (Companion.navigationTypePath.equals("tv", ignoreCase = true)) {
                    if (tvItemAdapter != null) {
                        tvItemAdapter!!.filter.filter("")
                        tvItemAdapter!!.page = data!!.getPage()
                        tvItemAdapter!!.addItems(data.getResults())
                        tvItemAdapter!!.notifyDataSetChanged()
                    } else {
                        tvItemAdapter =
                            TvItemAdapter(activity, data!!.getResults(), data.getTotal_pages())
                        listView!!.adapter = tvItemAdapter
                    }
                    setStatePost(StateType.GONE)
                }
            } else {
                if (context != null) {
                    if (apiRequest!!.hasNetwork(
                            context
                        )
                    ) {
                        tvItemAdapter = null
                        movieItemAdapter = null
                        setStatePost(StateType.GONE)
                        textView_not_Info!!.visibility = View.VISIBLE
                    } else {
                        setStatePost(StateType.RETRY)
                    }
                }
            }
        }

        override fun onFailure(call: Call<Any?>, t: Throwable) {
            Log.d(TAG, "onFailure")
            if (!call.isCanceled) setStatePost(StateType.RETRY)
        }


    }

    companion object {
        private const val TAG = "TAG_FRAGMENT"
        private const val ARG_SECTION_NUMBER = "section_number"
        private var navigationTypePath = "movie"
        private var apiRequest: ApiRequest? = null
        fun newInstance(index: Int): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            navigationTypePath = "movie"
            Log.d(TAG, "newInstance")
            return fragment
        }
    }
}