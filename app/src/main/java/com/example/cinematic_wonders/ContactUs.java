package com.example.cinematic_wonders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    EditText email;
    EditText message;
    Button send;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Message");

        email = findViewById(R.id.email);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);


    }


    public void Send(View view) {

        String newMessage = message.getText().toString();
        String newEmail = email.getText().toString();
        if (!newMessage.equals("")) {
            String key = reference.push().getKey();
            //Message m = new Message(key, newEmail, newMessage);
            reference.child(key).child("Email").setValue(newEmail);
            reference.child(key).child("Message").setValue(newMessage);
            Toast.makeText(this, "your message has been sent", Toast.LENGTH_SHORT).show();
        }

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