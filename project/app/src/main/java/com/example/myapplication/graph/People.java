package com.example.finalproject.graph;



import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class People {
    public People() throws FileNotFoundException {
        FileReader fileReader = new FileReader("D:\\university\\term3\\data structure\\project-final-eslamshahr\\users.json");
        Scanner scanner = new Scanner(fileReader);
        String text = scanner.useDelimiter("\\Z").next();
        Log.d("aaa", text);
    }
}
