package com.rappi.adminsion.data.remote.pelicula

import java.util.*

class MovieResponse {
    private var results: List<MovieItem?>? = null
    private var page = 0
    private var total_pages = 0

    fun getPage(): Int {
        return page
    }

    fun getTotal_pages(): Int {
        return total_pages
    }

    fun getResults(): List<MovieItem?>? {
        return results
    }

    fun MovieResponse(results: List<MovieItem?>?, page: Int, total_pages: Int) {
        this.results = results
        this.page = page
        this.total_pages = total_pages
    }

    class MovieItem(
        var id: Int,
        val vote_average: Double,
        val title: String,
        val backdrop_path: String,
        val release_date: Date
    )
}