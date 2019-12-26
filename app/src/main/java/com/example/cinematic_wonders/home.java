package com.example.cinematic_wonders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewFlipper;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    GridView grid;
    FirebaseDatabase database;
    DatabaseReference reference;
    private SliderLayout mDemoSlider;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        grid = findViewById(R.id.grid);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mDemoSlider = findViewById(R.id.slider);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Movie");
        slider();
        Grid();
    }

    private void slider() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> listImage = new ArrayList<>();
                ArrayList<String> listName = new ArrayList<>();
                for (DataSnapshot myChild : dataSnapshot.getChildren()) {
                    String img = myChild.child("Image").getValue(String.class);
                    String name = myChild.child("Name").getValue(String.class);
                    listImage.add(img);
                    listName.add(name);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.centerCrop();

                    for (int i = 0; i < listImage.size(); i++) {
                        TextSliderView sliderView = new TextSliderView(home.this);
                        // if you want show image only / without description text use DefaultSliderView instead

                        // initialize SliderLayout
                        sliderView
                                .image(listImage.get(i))
                                .description(listName.get(i))
                                .setRequestOption(requestOptions)
                                .setProgressBarVisible(true)
                                .setOnSliderClickListener(home.this);

                        //add your extra information
                        sliderView.bundle(new Bundle());
                        sliderView.getBundle().putString("extra", listName.get(i));
                        mDemoSlider.addSlider(sliderView);
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(4000);
                    mDemoSlider.addOnPageChangeListener(home.this);





                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

    public void Grid() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                final ArrayList<Movie> Array = new ArrayList<Movie>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Movie movie = child.getValue(Movie.class);
                    movie.id = child.getKey();
                    Array.add(movie);
                    // Log.d("TAG","onDataChanged"+Array);
                    MovieAdapter adapter = new MovieAdapter(home.this, R.layout.movie_grid_layout, Array);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(home.this, MoviePage.class);
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
                Log.d("theData", "onCancled " + databaseError);
            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
