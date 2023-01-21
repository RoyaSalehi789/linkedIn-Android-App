package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.myapplication.adapter.PeopleAdapter;
import com.example.myapplication.adapter.SpecialtiesSelectedAdapter;
import com.example.myapplication.databinding.ActivityProfileBinding;
import com.example.myapplication.databinding.ActivitySignUpBinding;
import com.example.myapplication.graph.GlobalInfo;
import com.example.myapplication.graph.Person;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SignUpActivity extends AppCompatActivity {

    static int id = 2000;
    int dateScore = 0;
    int fieldScore = 0;
    int workPlaceScore = 0;
    int universityScore = 0;
    int specialitiesScore = 0;

    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> specialitiesList = new ArrayList<>();

        specialitiesList.add("PHP");
        specialitiesList.add("Machine Learning");
        specialitiesList.add("Python");
        specialitiesList.add("Database");
        specialitiesList.add("Fast Type");
        specialitiesList.add("Photoshop");
        specialitiesList.add("c++");
        specialitiesList.add("Front-End");
        specialitiesList.add("Full-Stack");
        specialitiesList.add("C#");

        String []arraySpinner ={"1","2","3","4","5"};

        ArrayAdapter dateAdapter =new ArrayAdapter(this, R.layout.spinner_item, R.id.textView3, arraySpinner);
        ArrayAdapter fieldAdapter =new ArrayAdapter(this, R.layout.spinner_item, R.id.textView3, arraySpinner);
        ArrayAdapter workPlaceAdapter =new ArrayAdapter(this, R.layout.spinner_item, R.id.textView3, arraySpinner);
        ArrayAdapter uniAdapter =new ArrayAdapter(this, R.layout.spinner_item, R.id.textView3, arraySpinner);
        ArrayAdapter specialitiesAdapter =new ArrayAdapter(this, R.layout.spinner_item, R.id.textView3, arraySpinner);

        binding.dateScore.setAdapter(dateAdapter);
        binding.fieldScore.setAdapter(fieldAdapter);
        binding.workScore.setAdapter(workPlaceAdapter);
        binding.uniScore.setAdapter(uniAdapter);
        binding.specialitiesScore.setAdapter(specialitiesAdapter);


        binding.dateScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateScore = setScores(Integer.parseInt(binding.dateScore.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.fieldScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldScore = setScores(Integer.parseInt(binding.fieldScore.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.workScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workPlaceScore = setScores(Integer.parseInt(binding.workScore.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.uniScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                universityScore = setScores(Integer.parseInt(binding.uniScore.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.specialitiesScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialitiesScore = setScores(Integer.parseInt(binding.specialitiesScore.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayList<Integer> connectionsId = new ArrayList<>();

        GlobalInfo.getSpecialities().clear();
        SpecialtiesSelectedAdapter specialtiesSelectedAdapter = new SpecialtiesSelectedAdapter(specialitiesList);
        binding.specialtiesSignup.setHasFixedSize(true);
        binding.specialtiesSignup.setLayoutManager(new LinearLayoutManager(SignUpActivity.this));
        binding.specialtiesSignup.setAdapter(specialtiesSelectedAdapter);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.nameS.getText().toString();
                String email = binding.emailS.getText().toString();
                String dateOfBirth = binding.dateS.getText().toString();
                String field = binding.fieldS.getText().toString();
                String workplace = binding.workS.getText().toString();
                String university = binding.uniS.getText().toString();

                id++;
                Person person = new Person(id, name, dateOfBirth, university, field, workplace, email, GlobalInfo.getSpecialities(),
                        connectionsId, dateScore, universityScore, fieldScore, workPlaceScore, specialitiesScore);

                try {
                    GlobalInfo.getPeople(SignUpActivity.this).getPeople().add(person);
                    GlobalInfo.getPeople(SignUpActivity.this).getIdToPeople().put(person.getId(), person);
                    person.updateConnections(GlobalInfo.getPeople(SignUpActivity.this).getIdToPeople());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                GlobalInfo.setMe(person);

                Intent switchActivity = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(switchActivity);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(switchActivity);
            }
        });

    }
    int setScores(int priority){
        int score;
        switch(priority){
            case 1:
                score = 25;
                break;
            case 2:
                score = 20;
                break;
            case 3:
                score = 15;
                break;
            case 4:
                score = 10;
                break;
            case 5:
                score = 5;
                break;
            default:
                score = 0;
                break;
        }
        return score;
    }
}