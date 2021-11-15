package com.rappi.movietmd.network


import com.rappi.movietmdb.BuildConfig.API_KEY
import com.rappi.movietmdb.BuildConfig.BASE_URL

object TMDBApi {
    fun getMovie(): String {
        return BASE_URL + API_KEY +"&language=es-ES"
    }
}