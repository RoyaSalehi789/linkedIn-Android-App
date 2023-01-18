package com.example.myapplication.graph;

import android.util.Pair;

import org.json.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Person {
    int id;
    String name;
    String dateOfBirth;
    String universityLocation;
    String field;
    String workplace;
    ArrayList<String > specialties;
    ArrayList<Integer> connectionIds;
    ArrayList<Person> connections;

    public Person(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        dateOfBirth = jsonObject.getString("dateOfBirth");
        universityLocation = jsonObject.getString("universityLocation");
        field = jsonObject.getString("field");
        workplace = jsonObject.getString("workplace");

        JSONArray jsonArray = jsonObject.getJSONArray("specialties");
        specialties = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            specialties.add(jsonArray.getString(i));
        }

        jsonArray = jsonObject.getJSONArray("connectionId");
        connectionIds = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            connectionIds.add(jsonArray.getInt(i));
        }

        connections = new ArrayList<>();
    }

    public void updateConnections(Map<Integer, Person> idToPeople) {
        connections.clear();

        for (int id : connectionIds) {
            connections.add(idToPeople.get(id));
        }
    }

    public ArrayList<Pair<Person, Integer>> getConnectionsWithLevel(int maxLevel) {
        HashSet<Person> visited = new HashSet<>();
        ArrayList<Pair<Person, Integer>> result = new ArrayList<>();
        for (Person person : connections) {
            visited.add(person);
            result.add(new Pair<>(person, 1));
        }
        for (int i = 0; i < result.size(); i++) {
            Person person = result.get(i).first;
            int level = result.get(i).second + 1;
            if (level > maxLevel) {
                return result;
            }

            for (Person otherPerson : person.connections) {
                if (!visited.contains(otherPerson)) {
                    visited.add(otherPerson);
                    result.add(new Pair<>(otherPerson, level));
                }
            }
        }

        return result;
    }

    public Integer scoreRecommendation(Pair<Person, Integer> person) {
        int score = 0;
        if (dateOfBirth.equals(person.first.dateOfBirth)) {
            score += 1;
        }

        if (universityLocation.equals(person.first.universityLocation)) {
            score += 10;
        }

        if (field.equals(person.first.field)) {
            score += 10;
        }

        if (workplace.equals(person.first.workplace)) {
            score += 10;
        }

        for (int i = 0; i < specialties.size(); i++) {
            for (int j = 0; j < person.first.specialties.size(); j++) {
                if (specialties.get(i).equals(person.first.specialties.get(j))) {
                    score += 20;
                }
            }
        }

        score += 10 - person.second;

        return score;
    }

    public ArrayList<Pair<Person, Integer>> getRecommendation() {
        ArrayList<Pair<Person, Integer>> result = getConnectionsWithLevel(5);
        result.sort(new Comparator<Pair<Person, Integer>>() {
            @Override
            public int compare(Pair<Person, Integer> o1, Pair<Person, Integer> o2) {
                return scoreRecommendation(o2).compareTo(scoreRecommendation(o1));
            }
        });
        return result;
    }
}
