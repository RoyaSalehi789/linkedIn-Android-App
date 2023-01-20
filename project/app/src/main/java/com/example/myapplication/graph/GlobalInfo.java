package com.example.myapplication.graph;

import android.content.Context;

import com.example.myapplication.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GlobalInfo {
    static People people;
    static Person me = null;
    static Person other;

    public static People getPeople(Context context) throws JSONException, IOException {
        if (people == null) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getResources().openRawResource(R.raw.users))
            );
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            people = new People(sb.toString());
        }
        return people;
    }

    public static void setMe(Person me) {
        GlobalInfo.me = me;
    }

    public static Person getMe() {
        return me;
    }

    public static  Person getOther() { return other;}

    public static void setOther(Person other) {
        GlobalInfo.other = other;
    }
}
