package com.rappi.adminsion.data.remote.pelicula

import java.util.*

class MovieDetailsResponse {
    private var backdrop_path: String? = null
    private var budget = 0.0
    private var genres: List<Genres?>? = null
    private var name: String? = null
    private var id = 0
    private var original_language: String? = null
    private var original_title: String? = null
    private var overview: String? = null
    private var poster_path: String? = null
    private var release_date: Date? = null
    private var revenue = 0.0
    private var runtime = 0
    private var status: String? = null
    private var title: String? = null
    private var vote_average = 0f
    private var videos: Results? = null

    fun getBackdrop_path(): String? {
        return backdrop_path
    }

    fun getBudget(): Double {
        return budget
    }

    fun getGenres(): List<Genres?>? {
        return genres
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getOriginal_language(): String? {
        return original_language
    }

    fun getOriginal_title(): String? {
        return original_title
    }

    fun getOverview(): String? {
        return overview
    }

    fun getPoster_path(): String? {
        return poster_path
    }

    fun getRelease_date(): Date? {
        return release_date
    }

    fun getRevenue(): Double {
        return revenue
    }

    fun getRuntime(): Int {
        return runtime
    }

    fun getStatus(): String? {
        return status
    }

    fun getTitle(): String? {
        return title
    }

    fun getVote_average(): Float {
        return vote_average
    }

    fun getVideos(): Results? {
        return videos
    }

    fun MovieDetailsResponse(
        backdrop_path: String?,
        budget: Double,
        genres: List<Genres?>?,
        name: String?,
        id: Int,
        original_language: String?,
        original_title: String?,
        overview: String?,
        poster_path: String?,
        release_date: Date?,
        revenue: Double,
        runtime: Int,
        status: String?,
        title: String?,
        vote_average: Float,
        videos: Results?
    ) {
        this.backdrop_path = backdrop_path
        this.budget = budget
        this.genres = genres
        this.name = name
        this.id = id
        this.original_language = original_language
        this.original_title = original_title
        this.overview = overview
        this.poster_path = poster_path
        this.release_date = release_date
        this.revenue = revenue
        this.runtime = runtime
        this.status = status
        this.title = title
        this.vote_average = vote_average
        this.videos = videos
    }

    class Genres {
        var id = 0
        var name: String? = null
    }

    class Results(val results: List<Videos>)

    class Videos(val key: String, val site: String)
}