package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.graph.GlobalInfo;
import com.example.myapplication.graph.Person;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         binding.login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name = binding.nameInput.getText().toString();
                 String id = binding.idInput.getText().toString();
                 if (name.equals("") || id.equals("")) {
                     Toast.makeText(MainActivity.this, "Please fill the inputs", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     try {
                         int checkId = Integer.parseInt(id);
                         ArrayList<Person> loginIterate = GlobalInfo.getPeople(MainActivity.this).getPeople();
                         for (int i = 0; i < loginIterate.size(); i++) {
                             if (name.equals(loginIterate.get(i).getName()) && checkId == loginIterate.get(i).getId()) {
                                 GlobalInfo.setMe(loginIterate.get(i));
                                 Intent switchActivity = new Intent(MainActivity.this, HomeActivity.class);
                                 startActivity(switchActivity);
                             }
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             }
         });
    }
}