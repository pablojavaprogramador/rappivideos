package com.rappi.adminsion.data.remote.serie

import java.util.*

class TvDetailsResponse {
    private var id = 0
    private var name: String? = null
    private var backdrop_path: String? = null
    private var poster_path: String? = null
    private var vote_average = 0f
    private var overview: String? = null
    private var original_name: String? = null
    private var status: String? = null
    private lateinit var episode_run_time: IntArray
    private var first_air_date: Date? = null
    private var next_episode_to_air: NextEpisodeToAir? = null
    private var original_language: String? = null
    private var number_of_episodes = 0
    private var number_of_seasons = 0
    private var genres: List<Genres?>? = null
    private var videos: Results? = null

    fun getBackdrop_path(): String? {
        return backdrop_path
    }

    fun getEpisode_run_time(): IntArray? {
        return episode_run_time
    }

    fun getFirst_air_date(): Date? {
        return first_air_date
    }

    fun getGenres(): List<Genres?>? {
        return genres
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String? {
        return name
    }

    fun getOriginal_language(): String? {
        return original_language
    }

    fun getOriginal_name(): String? {
        return original_name
    }

    fun getOverview(): String? {
        return overview
    }

    fun getPoster_path(): String? {
        return poster_path
    }

    fun getStatus(): String? {
        return status
    }

    fun getVote_average(): Float {
        return vote_average
    }

    fun getNumber_of_episodes(): Int {
        return number_of_episodes
    }

    fun getNumber_of_seasons(): Int {
        return number_of_seasons
    }

    fun getNextEpisodeToAir(): NextEpisodeToAir? {
        return next_episode_to_air
    }

    fun getVideos(): Results? {
        return videos
    }

    class NextEpisodeToAir(val air_date: Date)

    fun TvDetailsResponse(
        id: Int,
        name: String?,
        backdrop_path: String?,
        poster_path: String?,
        vote_average: Float,
        overview: String?,
        original_name: String?,
        status: String?,
        episode_run_time: IntArray,
        first_air_date: Date?,
        next_episode_to_air: NextEpisodeToAir?,
        original_language: String?,
        number_of_episodes: Int,
        number_of_seasons: Int,
        genres: List<Genres?>?,
        videos: Results?
    ) {
        this.id = id
        this.name = name
        this.backdrop_path = backdrop_path
        this.poster_path = poster_path
        this.vote_average = vote_average
        this.overview = overview
        this.original_name = original_name
        this.status = status
        this.episode_run_time = episode_run_time
        this.first_air_date = first_air_date
        this.next_episode_to_air = next_episode_to_air
        this.original_language = original_language
        this.number_of_episodes = number_of_episodes
        this.number_of_seasons = number_of_seasons
        this.genres = genres
        this.videos = videos
    }

    class Genres {
        var id = 0
        var name: String? = null
    }

    class Results(val results: List<Videos>)

    class Videos(val key: String, val site: String)

}