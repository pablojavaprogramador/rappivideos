package com.rappi.movietmdb;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rappi.movietmd.network.ApiRepository
import com.rappi.movietmdb.detail.DetallesActivity

import com.rappi.movietmdb.model.Movie
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity(), Mainview {


    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private var movies: MutableList<Movie> = mutableListOf()
    private lateinit var listMovie : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     linearLayout {

         orientation = LinearLayout.VERTICAL
         padding = dip(16)

         listMovie = recyclerView {
             layoutManager = GridLayoutManager(ctx, 2)
         }
     }
        adapter = MainAdapter(movies){
        startActivity<DetallesActivity>(
            "TITLE" to it.title,
            "POSTER" to it.poster,
            "OVERVIEW" to it.overview
        )
        }
        listMovie.adapter = adapter
        presenter = MainPresenter(this, ApiRepository(), Gson())
        presenter.getMovieList()
     }

    override fun showMovieList(data: List<Movie>) {
       movies.clear()
        movies.addAll(data)
        adapter.notifyDataSetChanged()
    }

    }



