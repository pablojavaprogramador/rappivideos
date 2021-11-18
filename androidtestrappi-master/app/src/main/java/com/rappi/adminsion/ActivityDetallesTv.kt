package com.rappi.adminsion

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.rappi.adminsion.data.remote.ApiRequest
import com.rappi.adminsion.data.remote.CallbackResponseTvSerie
import com.rappi.adminsion.data.remote.GetServiceThemoviedb
import com.rappi.adminsion.data.remote.serie.TvDetailsResponse
import com.rappi.adminsion.data.remote.serieCallback
import com.rappi.adminsion.databinding.ActivityDetailsTvBinding
import com.rappi.adminsion.util.Constant.YOUTUBE_API_KEY
import java.text.SimpleDateFormat
import java.util.*

class ActivityDetallesTv  : AppCompatActivity() , serieCallback {

    private val callbackResponse = CallbackResponseTvSerie(this)
    private var idVideo: String? = null
    private var binding: ActivityDetailsTvBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsTvBinding.inflate(layoutInflater)
        setContentView(binding?.getRoot())
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbar_gradient))
        val apiRequest = ApiRequest(applicationContext)
        val bundle = this.intent.extras
        var tvId = 0
        if (bundle != null) {
            tvId = bundle.getInt("TvId")
        }
        if (tvId != 0) {
            apiRequest.getDataDetails(callbackResponse, "tv", tvId, applicationContext)
            binding?.buttonPlayTrailer?.setOnClickListener(View.OnClickListener {
                if (idVideo != null) {
                    val intent = YouTubeStandalonePlayer.createVideoIntent(
                        this@ActivityDetallesTv,
                        YOUTUBE_API_KEY,
                        idVideo,
                        0,
                        true,
                        true
                    )
                    startActivity(intent)
                }
            })
        } else {
            finish()
        }
    }


    override fun OnserieResponse(data: TvDetailsResponse) {
        val MOVIE_BASE_URL = GetServiceThemoviedb.URL_IMG + "t/p/w780"
        if (data != null) {
            if (data.getBackdrop_path() != null) {
                binding?.imageBackdropTv?.let {
                    Glide.with(applicationContext)
                        .load(MOVIE_BASE_URL + data.getBackdrop_path())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.placeholder)
                        .thumbnail(0.5f)
                        .into(it)
                }
            }
            if (data.getPoster_path() != null) {
                binding?.imagePosterTv?.let {
                    Glide.with(applicationContext)
                        .load(MOVIE_BASE_URL + data.getPoster_path())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.placeholder)
                        .thumbnail(0.5f)
                        .into(it)
                }
            }
            binding?.textViewTitle?.setText(data.getName())
            binding?.progressBarUserScore?.setProgress((data.getVote_average() * 10).toInt())
            binding?.textViewUserScoreValue?.setText(
                getString(
                    R.string.score_value,
                    binding?.progressBarUserScore?.getProgress()
                )
            )
            if (data.getVideos()!!.results != null && data.getVideos()!!.results.size > 0) {
                if (data.getVideos()!!.results[0].site.equals("YouTube", ignoreCase = true)) {
                    binding?.buttonPlayTrailer?.setVisibility(View.VISIBLE)
                    idVideo = data.getVideos()!!.results[0].key
                }
            }
            binding?.textViewTitleDescription?.setText(data.getOverview())
            binding?.textViewOriginalName?.setText(data.getOriginal_name())
            binding?.textViewState?.setText(data.getStatus())
            if (data.getEpisode_run_time()?.size!! > 0) {
                binding?.textViewEpisodeRunTime?.setText(
                    String.format(
                        Locale.getDefault(),
                        "%dh %02dm",
                        data.getEpisode_run_time()!![0] / 60,
                        data.getEpisode_run_time()!![0] % 60
                    )
                )
            } else {
                binding?.textViewEpisodeRunTime?.setText("-")
            }
            binding?.textViewFirstAirDate?.setText(
                SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(
                    data.getFirst_air_date()!!.time
                )
            )
            if (data.getNextEpisodeToAir() != null) {
                binding?.textViewFirstAirDate?.setText(
                    SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(
                        data.getNextEpisodeToAir()!!.air_date.time
                    )
                )
            } else {
                binding?.textViewFirstAirDate?.setText("-")
            }
            binding?.textViewOriginalLanguage?.setText(data.getOriginal_language())
            binding?.textViewNumberOfEpisodes?.setText(data.getNumber_of_episodes().toString())
            binding?.textViewNumberOfSeasons?.setText(data.getNumber_of_seasons().toString())
            var genres = ""
            for (i in data.getGenres()!!.indices) {
                genres = genres + data.getGenres()!![i]!!.name + ", "
            }
            if (genres.trim { it <= ' ' }.isEmpty()) genres = "-, "
            binding?.textViewGenres?.setText(genres.substring(0, genres.length - 2))
            binding?.layoutDescription?.setVisibility(View.VISIBLE)
            binding?.layoutInfo?.setVisibility(View.VISIBLE)
            binding?.progressBarData?.setVisibility(View.GONE)
        } else {
            Toast.makeText(applicationContext, R.string.Info_not_available, Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }


}