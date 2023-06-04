package com.example.athentication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class DiseaseModel implements Serializable, Parcelable {
    private String disease;
    private String description;
    private ArrayList<String> symptoms;

    public DiseaseModel(String disease, String description, ArrayList<String> symptoms) {
        this.disease = disease;
        this.description = description;
        this.symptoms = symptoms;
    }

    public DiseaseModel() {
    }

    protected DiseaseModel(Parcel in) {
        disease = in.readString();
        description = in.readString();
        symptoms = in.createStringArrayList();
    }

    public static final Creator<DiseaseModel> CREATOR = new Creator<DiseaseModel>() {
        @Override
        public DiseaseModel createFromParcel(Parcel in) {
            return new DiseaseModel(in);
        }

        @Override
        public DiseaseModel[] newArray(int size) {
            return new DiseaseModel[size];
        }
    };

    public String getDisease() {
        return disease;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(disease);
        dest.writeString(description);
        dest.writeStringList(symptoms);
    }
}
