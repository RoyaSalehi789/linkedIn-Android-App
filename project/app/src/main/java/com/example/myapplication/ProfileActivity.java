package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.adapter.PeopleAdapter;
import com.example.myapplication.adapter.SpecialtiesAdapter;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ActivityProfileBinding;
import com.example.myapplication.graph.GlobalInfo;

import org.json.JSONException;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.date2.setText(GlobalInfo.getOther().getDateOfBirth());
        binding.field2.setText(GlobalInfo.getOther().getField());
        binding.id2.setText(String.valueOf(GlobalInfo.getOther().getId()));
        binding.name2.setText(GlobalInfo.getOther().getName());
        binding.university2.setText(GlobalInfo.getOther().getUniversityLocation());
        binding.work2.setText(GlobalInfo.getOther().getWorkplace());

        SpecialtiesAdapter specialtiesAdapter = new SpecialtiesAdapter(GlobalInfo.getOther().getSpecialties());
        binding.specialtiesRecyclerView2.setHasFixedSize(true);
        binding.specialtiesRecyclerView2.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
        binding.specialtiesRecyclerView2.setAdapter(specialtiesAdapter);

        PeopleAdapter connectionsAdapter = new PeopleAdapter(GlobalInfo.getOther().getConnections(), this);
        binding.connectionsRecycler2.setHasFixedSize(true);
        binding.connectionsRecycler2.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
        binding.connectionsRecycler2.setAdapter(connectionsAdapter);

        for (int i = 0; i < GlobalInfo.getMe().getConnectionIds().size(); i++) {
            if (GlobalInfo.getMe().getConnectionIds().get(i) == GlobalInfo.getOther().getId()) {
                binding.connect.setText("Disconnect");
                break;
            }
        }

        binding.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.connect.getText().toString().equals("Disconnect")) {
                    int i = GlobalInfo.getMe().getConnectionIds().indexOf(GlobalInfo.getOther().getId());
                    GlobalInfo.getMe().getConnectionIds().remove(i);
                    i = GlobalInfo.getOther().getConnectionIds().indexOf(GlobalInfo.getMe().getId());
                    GlobalInfo.getOther().getConnectionIds().remove(i);
                    try {
                        GlobalInfo.getMe().updateConnections(GlobalInfo.getPeople(ProfileActivity.this).getIdToPeople());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.connect.setText("Connect");
                }
                else {
                    GlobalInfo.getMe().getConnectionIds().add(GlobalInfo.getOther().getId());
                    GlobalInfo.getOther().getConnectionIds().add(GlobalInfo.getMe().getId());
                    try {
                        GlobalInfo.getMe().updateConnections(GlobalInfo.getPeople(ProfileActivity.this).getIdToPeople());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.connect.setText("Disconnect");
                }
            }
        });
    }
}