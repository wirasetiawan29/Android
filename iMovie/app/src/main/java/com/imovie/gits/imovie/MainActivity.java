package com.imovie.gits.imovie;

/**
 * Created by wirasetiawan29 on 16/01/2015.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.imovie.gits.imovie.Movie.Movies;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Context ctx = this;
    // json object response url
    private String urlJsonObj = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?limit=20&country=us&apikey=uvrqb359zvra62qrbecfgry7";


    private ProgressDialog pDialog;
    Button button;
    ImageView thumbnail;

    private List<Movies> movieList = new ArrayList<Movies>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button =  (Button) findViewById(R.id.tes);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        //thumbnail = (ImageView) findViewById(R.id.thumbnail);

        RequestQueue queue = Volley.newRequestQueue(ctx);

        StringRequest stringRequest = new StringRequest(urlJsonObj,
                responseSuccess, responseError);

        queue.add(stringRequest);


    }

    //Respone
    Response.Listener<String> responseSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            Log.d("response", s);
            hidePDialog();

            try{

                JSONObject jsonObject = new JSONObject(s);
                JSONArray movie = jsonObject.getJSONArray("movies");
                Log.d("movie array", movie.toString());

                ListFilem film = new Gson().fromJson(s, ListFilem.class);

                for(int i = 0;i<film.getMovies().size() ;i++){
                    Movies pelem = new Movies();
                    pelem.setPosters(film.getMovies().get(i).getPosters());
                    pelem.setTitle(film.getMovies().get(i).getTitle());
                    pelem.setMpaa_rating(film.getMovies().get(i).getMpaa_rating());
                    pelem.setYear(film.getMovies().get(i).getYear());
                    pelem.setSynopsis(film.getMovies().get(i).getSynopsis());
                    movieList.add(pelem);
                }

                // movieList.addAll(film);
                String synopsis = film.getMovies().get(0).getSynopsis();
                Log.d("Synopsis", synopsis);

                String urlThumb = film.getMovies().get(0).getPosters().getThumbnail();



            }catch(JSONException e){}
            adapter.notifyDataSetChanged();
            hidePDialog();

            listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String title = ((TextView) view.findViewById(R.id.title))
//                            .getText().toString();
//                    String rating = ((TextView) view.findViewById(R.id.rating))
//                            .getText().toString();
//                    String year = ((TextView) view.findViewById(R.id.releaseYear))
//                            .getText().toString();
//                    String synopsis = ((TextView) view.findViewById(R.id.synopsis))
//                            .getText().toString();

                    Movies m = movieList.get(position);
                    Intent intent = new Intent(getApplicationContext(), DetailMovie.class);
                    intent.putExtra(DetailMovie.TAG_TITLE, m.getTitle());
                    intent.putExtra(DetailMovie.TAG_RATING, m.getMpaa_rating());
                    intent.putExtra(DetailMovie.TAG_YEAR, m.getYear());
                    intent.putExtra(DetailMovie.TAG_SYNOPSIS, m.getSynopsis());
                    startActivity(intent);
                }
            });
        }

    };
    //Error
    Response.ErrorListener responseError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ListFilem{
        List<Movies> movies;

        public List<Movies> getMovies() {
            return movies;
        }
    }
}
