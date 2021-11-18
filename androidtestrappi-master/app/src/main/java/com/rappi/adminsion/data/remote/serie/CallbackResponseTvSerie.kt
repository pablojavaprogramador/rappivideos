package com.rappi.adminsion.data.remote;

import android.util.Log
import com.rappi.adminsion.ActivityDetallesPelicula
import com.rappi.adminsion.ActivityDetallesTv
import com.rappi.adminsion.data.remote.pelicula.MovieDetailsResponse
import com.rappi.adminsion.data.remote.serie.TvDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CallbackResponseTvSerie(val callback: ActivityDetallesTv) : Callback<Any>{


    override fun onResponse(call: Call<Any>, response: Response<Any>) {
        val data = response.body() as TvDetailsResponse?
        val MOVIE_BASE_URL = GetServiceThemoviedb.URL_IMG + "t/p/w780"
        if (data != null) {
            callback.OnserieResponse(data)
   Log.i("","")
        } else {

        }
    }

    override  fun onFailure(call: Call<Any>, t: Throwable) {

    }


}

interface serieCallback{
    fun OnserieResponse(data :TvDetailsResponse)

}