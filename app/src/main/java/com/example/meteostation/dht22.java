package com.example.meteostation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meteostation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dht22 extends AppCompatActivity {
    private TextView dht22TempTextView,dht22HumTextView;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht22);
        dht22TempTextView = findViewById(R.id.t1dht22);
        dht22HumTextView = findViewById(R.id.t2dht22);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String dht22Temp = getData(dataSnapshot, "dht22/temp");

                String dht22Hum = getData(dataSnapshot, "dht22/hum");



                updateTextView(dht22TempTextView,  dht22Temp + "°C");

                updateTextView(dht22HumTextView,  dht22Hum + "%");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
            }
        });
    }

    private String getData(DataSnapshot dataSnapshot, String key) {
        return dataSnapshot.child(key).getValue(String.class);
    }

    private void updateTextView(TextView textView, String data) {
        textView.setText(data);
    }
}