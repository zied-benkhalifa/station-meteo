package com.example.meteostation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
public class dht11 extends AppCompatActivity {
    private TextView dht11TempTextView,dht11HumTextView;
    private DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht11);
        dht11TempTextView = findViewById(R.id.t1dht11);
        dht11HumTextView = findViewById(R.id.t2dht11);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String dht11Temp = getData(dataSnapshot, "dht11/temp");

                String dht11Hum = getData(dataSnapshot, "dht11/hum");



                updateTextView(dht11TempTextView,  dht11Temp + "°C");

                updateTextView(dht11HumTextView,  dht11Hum + "%");

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