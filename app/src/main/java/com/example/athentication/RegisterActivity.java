package com.example.athentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    TextInputEditText emailEditTxt, pwEditTxt, nameEditTxt;
    AppCompatButton registerBtn;
    TextView loginBtn;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    ProgressBar progressBar;

    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SignUp();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    private void SignUp(){
        nameEditTxt = findViewById(R.id.NameEditTxt);
        emailEditTxt = findViewById(R.id.EmailEditTxt);
        pwEditTxt = findViewById(R.id.PasswordEditTxt);

        registerBtn = findViewById(R.id.SignUpButton);

        loginBtn = findViewById(R.id.SignInText);

        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditTxt.getText().toString().trim();
                String password = pwEditTxt.getText().toString().trim();
                String name = nameEditTxt.getText().toString().trim();


                if (TextUtils.isEmpty(name))
                {
                    nameEditTxt.setError("Name is required!");
                    return;
                }

                //email processing
                if (TextUtils.isEmpty(email))
                {
                    emailEditTxt.setError("Email is required!");
                    return;
                }

                { //pw processing block
                    if (TextUtils.isEmpty(password)) {
                        pwEditTxt.setError("Password is required!");
                        return;
                    } else if (password.length() < 6) {
                        pwEditTxt.setError("Password must be more than 6 characters!");
                        return;
                    }
                }
                    progressBar.setVisibility(view.VISIBLE);
                    //registering user into firebase
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                //get the current user
                                FirebaseUser user = auth.getCurrentUser();
                                //display feedback
                                //store user id into firebase
                                DocumentReference df = fstore.collection("Users").document(user.getUid());
                                Map<String,Object> userInfor = new HashMap<>(); //represents key, value
                                //can be used to categorise our data and organize it
                                userInfor.put("Name", nameEditTxt.getText().toString());//user name categorie
                                userInfor.put("Email", email); //email categorie
                                userInfor.put("uid", user.getUid());

                                ArrayList<String> following = new ArrayList<>();
                                ArrayList<String> followers = new ArrayList<>();
                                ArrayList<String>  posts = new ArrayList<>();
                                ArrayList<String>  answers = new ArrayList<>();

                                df.set(userInfor).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        user.sendEmailVerification();
                                        Toast.makeText(RegisterActivity.this, "Please verifiy your email and sign in", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), VerificationActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }); //pass our map to the fb document
                            }
                            else
                            {
                                //if registration wasn't successful get the error (debugging purpose can be removed later)
                                Toast.makeText(RegisterActivity.this, "Some error has occured " + task.getException().getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

            }
        });
    }
}

