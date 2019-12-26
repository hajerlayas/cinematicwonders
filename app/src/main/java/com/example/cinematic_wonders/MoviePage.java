package com.example.cinematic_wonders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviePage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;

    ImageView imageView;
    TextView name;
    TextView type;
    TextView description;

    ArrayList<String> movieDate;
    ArrayList<String> movieTime;
    String movieID;
    String moviePrice;
    String movieName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Movie").child("MovieId");
        imageView = findViewById(R.id.img);
        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        description = findViewById(R.id.decs);

        Intent intent = getIntent();

        movieName = intent.getStringExtra("name");
        String movieDesc = intent.getStringExtra("desc");
        String movieImg = intent.getStringExtra("img");
        String movieType = intent.getStringExtra("type");
         movieDate = intent.getStringArrayListExtra("date");
         movieTime = intent.getStringArrayListExtra("time");
         movieID = intent.getStringExtra("id");
         moviePrice = intent.getStringExtra("Price");

        reference = database.getReference("Movie").child(movieID);



        name.setText(movieName);
        type.setText(movieType);
        description.setText(movieDesc);
        Picasso.get().load(movieImg).into(imageView);


    }


    public void booking(View view) {
        Intent intent = new Intent(this,bookingPage.class);
        intent.putStringArrayListExtra("date",movieDate);
        intent.putStringArrayListExtra("time", movieTime);
        intent.putExtra("id", movieID);
        intent.putExtra("Price",moviePrice);
        intent.putExtra("name",movieName);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public void goToMovies(View view) {
        Intent intent = new Intent(this, show_Movies.class);
        startActivity(intent);
    }

    public void goToContactUs(View view) {
        Intent intent = new Intent(this, ContactUs.class);
        startActivity(intent);
    }
}
