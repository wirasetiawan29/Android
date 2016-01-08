package com.imovie.gits.imovie;

/**
 * Created by wirasetiawan29 on 16/01/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imovie.gits.imovie.Movie.Movies;
import com.imovie.gits.imovie.Movie.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wirasetiawan29 on 15/01/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<Movies> movieItems;

    public CustomListAdapter(Activity activity, List<Movies> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }



    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int position) {
        return movieItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.row, null);

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        //TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        Movies m = movieItems.get(position);

        Picasso.with(activity)
                .load(m.getPosters().getThumbnail())
                .placeholder(R.drawable.ic_launcher)
                .fit()
                .into(thumbnail);

        title.setText(m.getTitle());
        rating.setText(m.getMpaa_rating());
        year.setText(m.getYear());

        return convertView;
    }
}
