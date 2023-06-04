package com.example.athentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class DiseaseDetailsActivity extends AppCompatActivity {

    TextView diseaseName, diseaseDescription, diseaseSymptoms;
    ImageButton backBtn;

    DiseaseModel diseaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);

        diseaseName = findViewById(R.id.disease_name);
        diseaseDescription = findViewById(R.id.disease_description_text);
        diseaseSymptoms = findViewById(R.id.disease_symptoms);
        backBtn = findViewById(R.id.back_button);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DiseaseDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        // Retrieve the DiseaseModel from the intent extras
        Intent intent = getIntent();
        if (intent != null) {
            diseaseModel = intent.getParcelableExtra("diseaseModel");
            Log.d("DiseaseDetailsActivity", "DiseaseModel: " + diseaseModel);
        }
        else
            Log.d("DiseaseDetailsActivity", "Intent is null");

        // Use the diseaseModel object as needed
        if (diseaseModel != null) {
            // Access the properties of the diseaseModel
            String diseaseName = diseaseModel.getDisease();
            String description = diseaseModel.getDescription();
            ArrayList<String> symptoms = diseaseModel.getSymptoms();

            // Set the text of the TextViews
            this.diseaseName.setText(diseaseName);
            this.diseaseDescription.setText(description);
            String symptomsString = "";
            for (String symptom : symptoms) {
                symptom = symptom.replace("_", " ");
                symptom.trim();
                symptomsString += symptom + "\n";
            }
            this.diseaseSymptoms.setText(symptomsString);
        }

    }
}