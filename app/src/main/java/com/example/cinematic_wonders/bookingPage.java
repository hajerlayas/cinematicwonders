package com.example.cinematic_wonders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class bookingPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference1;
    Spinner spinner;
    Spinner typeSpinner;
    private SweetAlertDialog sDialog;
    Boolean finalIfStatment = false;
    ArrayList<String> movieDate;
    ArrayList<String> movieTime;
    String movieName;
    String moviePrice;
    String movieID;
    String totalprice;
    int NumberOfSeats;
    int TotalPrice;
    String Seats;
    EditText Nmae;
    EditText Phone;
    EditText SeatsEV;
    Boolean save;
    TextView price;
    String MovieDATE;
    String MovieTIME;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        database = FirebaseDatabase.getInstance();


        Intent intent = getIntent();
        movieDate = intent.getStringArrayListExtra("date");
        movieTime = intent.getStringArrayListExtra("time");
        movieID = intent.getStringExtra("id");
        moviePrice = intent.getStringExtra("Price");
        movieName = intent.getStringExtra("name");
        Nmae = findViewById(R.id.nameeee);
        Phone = findViewById(R.id.phoneEV);
        SeatsEV = findViewById(R.id.saetEV);
        price = findViewById(R.id.price);

        price.setText(moviePrice);

        reference = database.getReference("Movie").child(movieID);
        reference1 = database.getReference().child("Customer");


        final List<String> dates = movieDate;
        final List<String> times = movieTime;


        typeSpinner = (Spinner) findViewById(R.id.day);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(bookingPage.this,
                android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.time);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(bookingPage.this,
                android.R.layout.simple_spinner_item, times);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);


    }

    public void booking(View view) {
        String Email = Nmae.getText().toString();
        String Phonee = Phone.getText().toString();
        Seats = SeatsEV.getText().toString();
        //NumberOfSeats =  Integer.valueOf(Seats);
        //TotalPrice = Integer.valueOf(moviePrice);
        MovieDATE =  typeSpinner.getSelectedItem().toString();
        MovieTIME =  spinner.getSelectedItem().toString();
        //totalPrice(moviePrice);

        // make an instant of cass customer with data
        Customer custumer = new Customer(Email, Phonee, Seats , movieName ,MovieDATE , MovieTIME , totalprice);
        save = save(custumer);


TextView textView = new TextView(this);
textView.setText(totalprice);



        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("total price :")
                .setConfirmText("book")
                .setCustomView(textView)
                .show();

    }


    private Boolean save(Customer custumer) {
        String key = reference1.push().getKey();

        reference1.child(key).setValue(custumer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                   // Toast.makeText(bookingPage.this, "TRUEE", Toast.LENGTH_SHORT).show();
                    finalIfStatment = true;
                } else {
                    Toast.makeText(bookingPage.this, "error with save function", Toast.LENGTH_SHORT).show();
                    finalIfStatment = false;

                }
            }

        });

        return finalIfStatment;
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

    private int totalPrice(int totalPriceNum) {
        totalPriceNum = totalPriceNum + NumberOfSeats;
        return totalPriceNum;
    }

}







