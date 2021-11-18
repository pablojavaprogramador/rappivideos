package com.rappi.adminsion.data.remote;

import android.util.Log
import com.rappi.adminsion.ActivityDetallesPelicula
import com.rappi.adminsion.data.remote.pelicula.MovieDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CallbackResponsePelicula(val callback: ActivityDetallesPelicula) : Callback<Any>{


    override fun onResponse(call: Call<Any>, response: Response<Any>) {
        val data = response.body() as MovieDetailsResponse?
        val MOVIE_BASE_URL = GetServiceThemoviedb.URL_IMG + "t/p/w780"
        if (data != null) {
            callback.OnmovieResponse(data)
   Log.i("","")
        } else {

        }
    }

    override  fun onFailure(call: Call<Any>, t: Throwable) {

    }


}

interface movieCallback{
    fun OnmovieResponse(data :MovieDetailsResponse)


}