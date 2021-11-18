package com.rappi.adminsion

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.rappi.adminsion.data.remote.*
import com.rappi.adminsion.data.remote.pelicula.MovieDetailsResponse
import com.rappi.adminsion.databinding.ActivityDetailsMovieBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class ActivityDetallesPelicula : AppCompatActivity() , movieCallback {
    private val YOUTUBE_API_KEY = "AIzaSyBsOoXmr--RxNv0v8o7aFOsOkcehN--s4U"
    private val callbackResponse = CallbackResponsePelicula(this)
    private var idVideo: String? = null
    private var binding: ActivityDetailsMovieBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(getLayoutInflater())
        setContentView(binding!!.getRoot())
        val actionBar: ActionBar? = getSupportActionBar()
        if (actionBar != null) actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_gradient))
        val apiRequest = ApiRequest(getApplicationContext())
        var movieId = 0
        val bundle: Bundle? = this.getIntent().getExtras()
        if (bundle != null) {
            movieId = bundle.getInt("MovieId")
        }
        if (movieId != 0) {
            apiRequest.getDataDetails(callbackResponse, "movie", movieId, getApplicationContext())
            binding!!.buttonPlayTrailer.setOnClickListener(View.OnClickListener {
                if (idVideo != null) {
                    val intent = YouTubeStandalonePlayer.createVideoIntent(
                        this@ActivityDetallesPelicula,
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


    override fun OnmovieResponse(data: MovieDetailsResponse) {
        val MOVIE_BASE_URL = GetServiceThemoviedb.URL_IMG + "t/p/w780"
        if (data != null) {
            Log.i("TODO", data.getOverview()!!)
            if (data.getBackdrop_path() != null) {
                binding?.imageBackdropTv?.let {
                    Glide.with(getApplicationContext())
                        .load(MOVIE_BASE_URL + data.getBackdrop_path())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.placeholder)
                        .thumbnail(0.5f)
                        .into(it)
                }
            }
            if (data.getPoster_path() != null) {
                binding?.let {
                    Glide.with(getApplicationContext())
                        .load(MOVIE_BASE_URL + data.getPoster_path())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.placeholder)
                        .thumbnail(0.5f)
                        .into(it.imagePosterMovie)
                }
            }
            binding?.textViewTitle?.setText(data.getTitle().toString())
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
                    // view.setVisibility(View.VISIBLE);
                    idVideo = data.getVideos()!!.results[0].key
                }
            }
            Log.i("LOG descripcion ", data.getOverview()!!)
            binding?.textViewDescription?.setText(data.getOverview())
            binding?.textViewOriginalTitle?.setText(data.getOriginal_title())
            binding?.textViewState?.setText(data.getStatus())
            binding?.textViewReleaseDate?.setText(
                SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(
                    data.getRelease_date()!!.time
                )
            )
            binding?.textViewOriginalLanguage?.setText(data.getOriginal_language())
            binding?.textViewRuntime?.setText(
                String.format(
                    Locale.getDefault(),
                    "%dh %02dm",
                    data.getRuntime() / 60,
                    data.getRuntime() % 60
                )
            )
            binding?.textViewBudget?.setText(
                DecimalFormat("$#,###", DecimalFormatSymbols()).format(
                    data.getBudget()
                )
            )
            binding?.textViewRevenue?.setText(
                DecimalFormat("$#,###", DecimalFormatSymbols()).format(
                    data.getRevenue()
                )
            )
            var genres = ""
            for (i in data.getGenres()!!.indices) {
                genres = genres + data.getGenres()!![i]!!.name + ", "
            }
            binding?.textViewGenres?.setText(genres.substring(0, genres.length - 2))
            binding?.layoutDescription?.setVisibility(View.VISIBLE)
            binding?.layoutInfo?.setVisibility(View.VISIBLE)
            binding?.progressBarData?.setVisibility(View.GONE)
        } else {
            Toast.makeText(getApplicationContext(), R.string.Info_not_available, Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

}