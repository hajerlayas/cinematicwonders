package com.example.cinematic_wonders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class show_Movies extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference1;
    DatabaseReference reference2;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);


        grid = findViewById(R.id.grid);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Type");
        reference1 = database.getReference().child("Movie");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> types = new ArrayList<String>();

                for (DataSnapshot typeSnapshot : dataSnapshot.getChildren()) {
                    String typeName = typeSnapshot.getValue(String.class);
                    types.add(typeName);

                    Spinner typeSpinner = (Spinner)findViewById(R.id.movieType);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(show_Movies.this,
                            android.R.layout.simple_spinner_item, types);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    typeSpinner.setAdapter(adapter);
                    typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            viewTypes(types.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        grid();

    }

    private void grid() {
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final ArrayList<Movie> Array = new ArrayList<Movie>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Movie movie = child.getValue(Movie.class);
                    movie.id = child.getKey();
                    Array.add(movie);
                    // Log.d("TAG","onDataChanged"+Array);
                    MovieAdapter adapter = new MovieAdapter(show_Movies.this, R.layout.movie_grid_layout, Array);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(show_Movies.this, MoviePage.class);
                            intent.putExtra("name", Array.get(i).Name);
                            intent.putExtra("desc", Array.get(i).Description);
                            intent.putExtra("img", Array.get(i).Image);
                            intent.putExtra("type", Array.get(i).Type);
                            intent.putStringArrayListExtra("date", Array.get(i).Dates);
                            intent.putStringArrayListExtra("time", Array.get(i).Times);
                            intent.putExtra("id", Array.get(i).id);
                            intent.putExtra("Price",Array.get(i).Price);

                            startActivity(intent);
                            //Log.d("TAG","onDataChanged"+Array);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void viewTypes(String SelectedType){

        reference2 = FirebaseDatabase.getInstance().getReference();

        Query query = database.getReference().child("Movie").orderByChild("Type").equalTo(SelectedType);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Movie> Array = new ArrayList<Movie>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Movie movie = child.getValue(Movie.class);
                    movie.id = child.getKey();
                    Array.add(movie);
                    MovieAdapter adapter = new MovieAdapter(show_Movies.this,R.layout.movie_grid_layout,Array);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(show_Movies.this, MoviePage.class);
                            intent.putExtra("name",Array.get(i).Name);
                            intent.putExtra("desc",Array.get(i).Description);
                            intent.putExtra("img",Array.get(i).Image);
                            intent.putExtra("type",Array.get(i).Type);
                            intent.putStringArrayListExtra("date", Array.get(i).Dates);
                            intent.putStringArrayListExtra("time", Array.get(i).Times);
                            intent.putExtra("id",Array.get(i).id);

                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void contactUs(View view) {
        Intent intent = new Intent(this, ContactUs.class);
        startActivity(intent);
    }

    public void goToMovies(View view) {
        Intent intent = new Intent(this, show_Movies.class);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
}