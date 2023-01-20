package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PeopleAdapter;
import com.example.myapplication.adapter.RecommendationAdapter;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.databinding.FragmentRecommendationsBinding;
import com.example.myapplication.graph.GlobalInfo;
import com.example.myapplication.graph.Person;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class RecommendationsFragment extends Fragment {
    FragmentRecommendationsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecommendationsBinding.inflate(inflater, container, false);

        ArrayList<Pair<Person, Integer>> recommendations = new ArrayList<>();
        ArrayList<Pair<Person, Integer>> temp = null;
        try {
            temp = GlobalInfo.getMe().getRecommendation(GlobalInfo.getPeople(requireContext()));
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).second != 1 &&
                        temp.get(i).first.getId() != GlobalInfo.getMe().getId()) {
                   recommendations.add(temp.get(i));
                   if (recommendations.size() == 20) break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(recommendations, requireContext());
        binding.recommendationRecyclerView.setHasFixedSize(true);
        binding.recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recommendationRecyclerView.setAdapter(recommendationAdapter);
        return binding.getRoot();
    }
}