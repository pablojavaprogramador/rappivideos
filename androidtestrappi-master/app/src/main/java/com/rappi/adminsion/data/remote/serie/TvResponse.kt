package com.rappi.adminsion.data.remote.serie

import java.util.*

class TvResponse {
    private var results: List<TvItem?>? = null
    private var page = 0
    private var total_pages = 0

    fun getPage(): Int {
        return page
    }

    fun getTotal_pages(): Int {
        return total_pages
    }

    fun getResults(): List<TvItem?>? {
        return results
    }

    fun TvResponse(results: List<TvItem?>?, page: Int, total_pages: Int) {
        this.results = results
        this.page = page
        this.total_pages = total_pages
    }

    class TvItem(
        var name: String,
        val first_air_date: Date,
        val backdrop_path: String,
        var id: Int,
        val vote_average: Double
    )

}