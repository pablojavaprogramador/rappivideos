package com.rappi.movietmdb;

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.rappi.movietmdb.BuildConfig.IMAGE_URL
import com.rappi.movietmdb.R
import com.rappi.movietmdb.model.Movie

import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainAdapter(private val result: List<Movie>, private val listener: (Movie) -> Unit)
    : RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieUI().createView(AnkoContext.Companion.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = result.size



    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       holder.bindItem(result[position], listener)
    }

}

class MovieUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {

                padding = dip(5)
                orientation = LinearLayout.VERTICAL

                imageView {
                    id = R.id.movie_poster
                }.lparams {
                    height = dip(250)
                    width = wrapContent
                }

                textView {
                    id = R.id.movie_title
                    textSize = 16f
                }.lparams {
                    margin = dip(16f)
                }
            }
        }
    }
}
class MovieViewHolder(view: View) :RecyclerView.ViewHolder(view){
    private val moviePoster: ImageView = view.find(R.id.movie_poster)
    private val movieTitlle: TextView = view.find(R.id.movie_title)
    fun bindItem(movies: Movie, listener: (Movie) -> Unit){
        Picasso.get().load(IMAGE_URL + movies.poster).into(moviePoster)
        Log.d("GAMBAR", "url ="+ IMAGE_URL+movies.poster)
        movieTitlle.text = movies.title

        moviePoster.onClick {
            listener(movies)
        }
    }
}