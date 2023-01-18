package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.graph.People;
import com.example.myapplication.graph.Person;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            People people = new People(
                    getResources().openRawResource(R.raw.users).toString()
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}