package com.example.athentication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DiseasesFragment extends Fragment {

    private View parentHolder;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private DiseasesAdapter adapter;
    private ImageButton settingsButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentHolder = inflater.inflate(R.layout.fragment_diseases, container, false);

        settingsButton = parentHolder.findViewById(R.id.settingsBtn);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to settings activity
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Diseases")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<DiseaseModel> diseaseList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DiseaseModel disease = document.toObject(DiseaseModel.class);
                            diseaseList.add(disease);
                            Log.d(TAG, disease.getDisease().toString());
                        }
                        BuildRecyclerView(diseaseList);
                    } else {
                        // Handle the error
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });


        return parentHolder;
    }

    //Build the recyclerView
    private void BuildRecyclerView(ArrayList<DiseaseModel> diseaseas) {
        Collections.sort(diseaseas, new Comparator<DiseaseModel>() {
            @Override
            public int compare(DiseaseModel disease1, DiseaseModel disease2) {
                return String.CASE_INSENSITIVE_ORDER.compare(disease1.getDisease(), disease2.getDisease());
            }
        });
        recyclerView = parentHolder.findViewById(R.id.recview);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new DiseasesAdapter(diseaseas, getContext());
        recyclerView.setAdapter(adapter);

    }
}