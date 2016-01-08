package com.imovie.gits.imovie;

/**
 * Created by wirasetiawan29 on 16/01/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class DetailMovie  extends Activity {

    // JSON node keys
    public static final String TAG_TITLE = "title";
    public static final String TAG_RATING = "rating";
    public static final String TAG_YEAR = "releaseYear";
    public static final String TAG_SYNOPSIS = "synopsis";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String title = in.getStringExtra(TAG_TITLE);
        String rating = in.getStringExtra(TAG_RATING);
        String year = in.getStringExtra(TAG_YEAR);
        String synopsis = in.getStringExtra(TAG_SYNOPSIS);

        // Displaying all values on the screen
        TextView lblTitle = (TextView) findViewById(R.id.title_label);
        TextView lblRating = (TextView) findViewById(R.id.rating_label);
        TextView lblYear = (TextView) findViewById(R.id.year_label);
        TextView lblSynopsis = (TextView) findViewById(R.id.synopsis);

        lblTitle.setText(title);
        lblRating.setText(rating);
        lblYear.setText(year);
        lblSynopsis.setText(synopsis);
    }
}

