package com.example.meteostation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bmp extends AppCompatActivity {
    private TextView  bmp280TempTextView, bmp280PressureTextView;
    private DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmp);

        bmp280TempTextView = findViewById(R.id.bmp1);
        bmp280PressureTextView= findViewById(R.id.bmp2);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String bmp280Temp = getData(dataSnapshot, "bmp280/temp");
                String bmp280Pressure = getData(dataSnapshot, "bmp280/pressure");


                updateTextView(bmp280TempTextView,  bmp280Temp + "°C");
                updateTextView(bmp280PressureTextView,  bmp280Pressure + "");


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