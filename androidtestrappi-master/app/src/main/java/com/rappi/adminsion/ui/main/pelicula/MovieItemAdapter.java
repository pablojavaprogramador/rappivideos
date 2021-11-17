package com.rappi.adminsion.ui.main.pelicula;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.rappi.adminsion.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rappi.adminsion.data.remote.GetServiceThemoviedb;
import com.rappi.adminsion.data.remote.pelicula.MovieResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieItemAdapter extends BaseAdapter implements Filterable {
    private static final String MOVIE_BASE_URL = GetServiceThemoviedb.URL_IMG + "t/p/w780";
    private final Context context;
    private List<MovieResponse.MovieItem> items;
    private final List<MovieResponse.MovieItem> itemsFiltered;
    private int page;
    private final int totalPages;



    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MovieResponse.MovieItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItems(List<MovieResponse.MovieItem> items) {
        if (this.items == null || this.items.size() == 0) {
            this.items = items;
        } else {
            this.items.addAll(items);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                rowView = inflater.inflate(R.layout.movie_item_list, parent, false);
            }
        }

        TextView title = rowView.findViewById(R.id.movie_item_title);
        TextView vote_average = rowView.findViewById(R.id.movie_item_vote_average);
        TextView release_date = rowView.findViewById(R.id.movie_item_release_date);
        ImageView poster = rowView.findViewById(R.id.movie_item_poster);

        MovieResponse.MovieItem item = items.get(position);

        title.setText(item.getTitle());
        vote_average.setText(String.valueOf(item.getVote_average()));
        if (item.getRelease_date() != null) {
            release_date.setText(new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(item.getRelease_date().getTime()));
        }
        if (item.getBackdrop_path() != null) {
            Glide.with(context)
                    .load(MOVIE_BASE_URL + item.getBackdrop_path())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.placeholder)
                    .thumbnail(0.5f)
                    .into(poster);
        }
        return rowView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                items = itemsFiltered;
                if (constraint == null || constraint.length() == 0) {
                    results.count = itemsFiltered.size();
                    results.values = itemsFiltered;
                } else {
                    List<MovieResponse.MovieItem> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (MovieResponse.MovieItem o : items)
                        if (o.getTitle().toUpperCase().contains(searchStr)) resultsData.add(o);
                    results.count = resultsData.size();

                    results.values = resultsData;
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<MovieResponse.MovieItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public boolean isFiltered() {
        return !items.equals(itemsFiltered);
    }

    public  MovieItemAdapter(Context context, List<MovieResponse.MovieItem> items, int totalPages) {
        this.context = context;
        this.items = items;
        this.itemsFiltered = items;
        this.page = 1;
        this.totalPages = totalPages;
    }

}
