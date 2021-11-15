package com.rappi.movietmdb;



import com.google.gson.Gson
import com.rappi.movietmd.network.ApiRepository
import com.rappi.movietmd.network.TMDBApi
import com.rappi.movietmdb.model.MovieResponse


import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class  MainPresenter(private val view: Mainview, private  val apiRepository: ApiRepository, private  val gson: Gson){
    fun getMovieList(){
        doAsync {
            val data  = gson.fromJson(apiRepository.doRequest(TMDBApi.getMovie()),
                MovieResponse::class.java)
            uiThread {
                view.showMovieList(data.results)
            }
        }
    }

}