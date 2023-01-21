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
    int dateOfBirthScore;
    String universityLocation;
    int universityLocationScore;
    String field;
    int fieldScore;
    String workplace;
    int workplaceScore;
    String email;
    ArrayList<String > specialties;
    int specialtiesScore;
    ArrayList<Integer> connectionIds;
    ArrayList<Person> connections;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUniversityLocation() {
        return universityLocation;
    }

    public String getField() {
        return field;
    }

    public String getWorkplace() {
        return workplace;
    }

    public ArrayList<String> getSpecialties() {
        return specialties;
    }

    public ArrayList<Integer> getConnectionIds() {
        return connectionIds;
    }

    public ArrayList<Person> getConnections() {
        return connections;
    }

    public int getDateOfBirthScore() {
        return dateOfBirthScore;
    }

    public void setDateOfBirthScore(int dateOfBirthScore) {
        this.dateOfBirthScore = dateOfBirthScore;
    }

    public int getUniversityLocationScore() {
        return universityLocationScore;
    }

    public void setUniversityLocationScore(int universityLocationScore) {
        this.universityLocationScore = universityLocationScore;
    }

    public int getFieldScore() {
        return fieldScore;
    }

    public void setFieldScore(int fieldScore) {
        this.fieldScore = fieldScore;
    }

    public int getWorkplaceScore() {
        return workplaceScore;
    }

    public void setWorkplaceScore(int workplaceScore) {
        this.workplaceScore = workplaceScore;
    }

    public int getSpecialtiesScore() {
        return specialtiesScore;
    }

    public void setSpecialtiesScore(int specialtiesScore) {
        this.specialtiesScore = specialtiesScore;
    }

    public Person(int id, String name, String dateOfBirth, String universityLocation,
                  String field, String workplace, String email, ArrayList<String > specialties,
                  ArrayList<Integer> connectionIds, int dateOfBirthScore, int universityLocationScore,
                    int fieldScore, int workplaceScore, int specialtiesScore) {
        this.dateOfBirthScore = dateOfBirthScore;
        this.universityLocationScore = universityLocationScore;
        this.fieldScore = fieldScore;
        this.workplaceScore = workplaceScore;
        this.specialtiesScore = specialtiesScore;

        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.universityLocation = universityLocation;
        this.field = field;
        this.workplace = workplace;
        this.email = email;
        this.specialties = specialties;
        this.connectionIds = connectionIds;

        connections = new ArrayList<>();
    }

    public Person(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        dateOfBirth = jsonObject.getString("dateOfBirth");
        universityLocation = jsonObject.getString("universityLocation");
        field = jsonObject.getString("field");
        workplace = jsonObject.getString("workplace");
        email = jsonObject.getString("email");

        dateOfBirthScore = 1;
        universityLocationScore = 10;
        fieldScore = 10;
        workplaceScore = 10;
        specialtiesScore = 20;

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

    public ArrayList<Pair<Person, Integer>> getConnectionsWithLevel(int maxLevel, People people) {
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

        if (people != null) {
            for (Person person : people.getPeople()) {
                if (!visited.contains(person)) {
                    result.add(new Pair<>(person, maxLevel + 2));
                }
            }
        }

        return result;
    }

    public Integer scoreRecommendation(Pair<Person, Integer> person, int dateOfBirthScore, int universityLocationScore,
                                       int fieldScore, int workplaceScore, int specialtiesScore) {
        int score = 0;
        if (dateOfBirth.equals(person.first.dateOfBirth)) {
            score += dateOfBirthScore;
        }

        if (universityLocation.equals(person.first.universityLocation)) {
            score += universityLocationScore;
        }

        if (field.equals(person.first.field)) {
            score += fieldScore;
        }

        if (workplace.equals(person.first.workplace)) {
            score += workplaceScore;
        }

        for (int i = 0; i < specialties.size(); i++) {
            for (int j = 0; j < person.first.specialties.size(); j++) {
                if (specialties.get(i).equals(person.first.specialties.get(j))) {
                    score += specialtiesScore;
                }
            }
        }

        score += 10 - person.second;

        return score;
    }

    public ArrayList<Pair<Person, Integer>> getRecommendation(People people) {
        ArrayList<Pair<Person, Integer>> result = getConnectionsWithLevel(5, people);
        result.sort(new Comparator<Pair<Person, Integer>>() {
            @Override
            public int compare(Pair<Person, Integer> o1, Pair<Person, Integer> o2) {
                return scoreRecommendation(o2, o2.first.dateOfBirthScore, o2.first.universityLocationScore,
                        o2.first.fieldScore, o2.first.workplaceScore, o2.first.specialtiesScore).compareTo(scoreRecommendation(o1,
                        o1.first.dateOfBirthScore, o1.first.universityLocationScore,
                        o1.first.fieldScore, o1.first.workplaceScore, o1.first.specialtiesScore));
            }
        });
        return result;
    }
}
