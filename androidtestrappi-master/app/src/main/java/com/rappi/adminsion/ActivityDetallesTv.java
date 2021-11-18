package com.rappi.adminsion;
import android.annotation.SuppressLint;
import android.content.Intent;
import com.rappi.adminsion.databinding.ActivityDetailsTvBinding;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.rappi.adminsion.data.remote.ApiRequest;
import com.rappi.adminsion.data.remote.CallbackResponseTvSerie;
import com.rappi.adminsion.data.remote.GetServiceThemoviedb;
import com.rappi.adminsion.data.remote.pelicula.MovieDetailsResponse;
import com.rappi.adminsion.data.remote.serie.TvDetailsResponse;
import com.rappi.adminsion.data.remote.serieCallback;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityDetallesTv extends AppCompatActivity implements serieCallback {
    private static final String YOUTUBE_API_KEY = "AIzaSyBsOoXmr--RxNv0v8o7aFOsOkcehN--s4U";
    private final CallbackResponseTvSerie callbackResponse = new CallbackResponseTvSerie(this);
    private String idVideo;
    private ActivityDetailsTvBinding binding= null;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding = ActivityDetailsTvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_gradient));

        ApiRequest apiRequest = new ApiRequest(getApplicationContext());
        Bundle bundle = this.getIntent().getExtras();
        int tvId = 0;
        if (bundle != null) {
            tvId = bundle.getInt("TvId");
        }

        if (tvId != 0) {
            apiRequest.getDataDetails(callbackResponse, "tv", tvId, getApplicationContext());


            binding.buttonPlayTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idVideo != null) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(ActivityDetallesTv.this, YOUTUBE_API_KEY, idVideo, 0, true, true);
                        startActivity(intent);
                    }
                }
            });

        } else {
            finish();
        }
    }


    @Override
    public void OnserieResponse(@NonNull TvDetailsResponse data) {
            final String MOVIE_BASE_URL = GetServiceThemoviedb.URL_IMG+ "t/p/w780";

            if (data != null) {

                if (data.getBackdrop_path() != null) {
                    Glide.with(getApplicationContext())
                            .load(MOVIE_BASE_URL + data.getBackdrop_path())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.placeholder)
                            .thumbnail(0.5f)
                            .into(binding.imageBackdropTv);
                }

                if (data.getPoster_path() != null) {
                    Glide.with(getApplicationContext())
                            .load(MOVIE_BASE_URL + data.getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.placeholder)
                            .thumbnail(0.5f)
                     .into(binding.imagePosterTv);
                }


                binding.textViewTitle.setText(data.getName());
                binding.progressBarUserScore.setProgress((int) (data.getVote_average() * 10));
                 binding.textViewUserScoreValue.setText(getString(R.string.score_value, binding.progressBarUserScore.getProgress()));
                if (data.getVideos().getResults() != null && data.getVideos().getResults().size() > 0) {
                    if (data.getVideos().getResults().get(0).getSite().equalsIgnoreCase("YouTube")) {

                        binding.buttonPlayTrailer.setVisibility(View.VISIBLE);
                        idVideo = data.getVideos().getResults().get(0).getKey();
                    }
                }


                binding.textViewTitleDescription.setText(data.getOverview());
                binding.textViewOriginalName.setText(data.getOriginal_name());

                binding.textViewState.setText(data.getStatus());
                if (data.getEpisode_run_time().length > 0) {

               binding.textViewEpisodeRunTime.setText(String.format(Locale.getDefault(), "%dh %02dm", data.getEpisode_run_time()[0] / 60, data.getEpisode_run_time()[0] % 60));
                } else {
                    binding.textViewEpisodeRunTime.setText("-");
                }
              binding.textViewFirstAirDate.setText(new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(data.getFirst_air_date().getTime()));
                if (data.getNextEpisodeToAir() != null) {
                      binding.textViewFirstAirDate.setText(new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(data.getNextEpisodeToAir().getAir_date().getTime()));
                } else {
                    binding.textViewFirstAirDate.setText("-");
                }

                binding.textViewOriginalLanguage.setText(data.getOriginal_language());
                binding.textViewNumberOfEpisodes.setText(String.valueOf(data.getNumber_of_episodes()));
                binding.textViewNumberOfSeasons.setText(String.valueOf(data.getNumber_of_seasons()));
                String genres = "";
                for (int i = 0; i < data.getGenres().size(); i++) {
                    genres = genres.concat(data.getGenres().get(i).getName().concat(", "));
                }
                if (genres.trim().isEmpty()) genres = "-, ";
                binding.textViewGenres.setText(genres.substring(0, genres.length() - 2));
                binding.layoutDescription.setVisibility(View.VISIBLE);
                binding.layoutInfo.setVisibility(View.VISIBLE);
                binding.progressBarData.setVisibility(View.GONE);

            } else {
                Toast.makeText(getApplicationContext(), R.string.Info_not_available, Toast.LENGTH_LONG).show();
                finish();
            }
        }




}
