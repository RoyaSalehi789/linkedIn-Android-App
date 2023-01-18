package com.example.myapplication.graph;



import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class People {

    ArrayList<Person> people;
    Map<Integer, Person> idToPeople = new HashMap<>();

    public People(String text) throws JSONException {

        people = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(text);
        for (int i = 0; i < jsonArray.length(); i++) {
            Person person = new Person(jsonArray.getJSONObject(i));
            idToPeople.put(person.id, person);
            people.add(person);
        }

        for (Person person : people) {
            person.updateConnections(idToPeople);
        }
    }
}
