package com.example.film;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.film.models.Movie;
import com.example.film.response.MovieResponse;
import com.example.film.service.MovieService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "707bf05a1656256f0c002d2ff92d8c5b";

    private RecyclerView popularRecyclerView;
    private RecyclerView todayRecyclerView;
    private MovieAdapter popularAdapter;
    private MovieAdapter todayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularRecyclerView = findViewById(R.id.popularRecyclerView);
        todayRecyclerView = findViewById(R.id.todayRecyclerView);

        popularAdapter = new MovieAdapter();
        todayAdapter = new MovieAdapter();

        popularRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        popularRecyclerView.setAdapter(popularAdapter);
        todayRecyclerView.setAdapter(todayAdapter);

        getPopularMovies();
        getTodayMovies();
    }

    private void getPopularMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        Call<MovieResponse> call = movieService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    if (movieResponse != null) {
                        List<Movie> movies = movieResponse.getResults();
                        popularAdapter.setMovies(movies.subList(0, 3));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get popular movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to get popular movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTodayMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        Call<MovieResponse> call = movieService.getNowPlayingMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    if (movieResponse != null) {
                        List<Movie> movies = movieResponse.getResults();
                        todayAdapter.setMovies(movies.subList(0, 5));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get today movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to get today movies", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
