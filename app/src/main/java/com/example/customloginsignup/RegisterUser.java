package com.example.customloginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    //3
    private TextView textViewBanner;
    private EditText editTextFullName,editTextAge,editTextEmailRegister,editTextPasswordRegister;
    private ProgressBar progressBarRegister;

    private Button registerUser;
    private FirebaseAuth mAuth;  //1


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth=FirebaseAuth.getInstance(); //2

        //<4
        textViewBanner = (TextView)findViewById(R.id.textViewBanner);//4
        textViewBanner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.buttonRegisterUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextEmailRegister = (EditText) findViewById(R.id.editTextEmailRegister);
        editTextPasswordRegister = (EditText) findViewById(R.id.editTextPasswordRegister);

        progressBarRegister = (ProgressBar) findViewById(R.id.progressBarRegister);
        //4>

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewBanner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.buttonRegisterUser:
                registerUser();
                break;
        }
    }

    private void registerUser(){//5
        String email = editTextEmailRegister.getText().toString().trim();
        String password = editTextPasswordRegister.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullName.setError("Full Name is required");
            editTextFullName.requestFocus();
            return;
        }

        if (age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextEmailRegister.setError("Email is required");
            editTextEmailRegister.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailRegister.setError("Please provide valid Email.");
            editTextEmailRegister.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPasswordRegister.setError("Password is required");
            editTextPasswordRegister.requestFocus();
            return;
        }

        if (password.length()<6){
            editTextPasswordRegister.setError("Min Password Length should be 6 char");
            editTextPasswordRegister.requestFocus();
            return;
        }
//6
        progressBarRegister.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)//pass the email and password which we have
                // now check if the user has already been registered
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){// if the user has been registered successfully
//                            now we create user object so that we solve user info in realTimeDataBase
                            User user = new User(fullName,age,email);
//                            now we send the object to firebase
                            FirebaseDatabase.getInstance().getReference("users")//collections name
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())//here we need to link the current user id with the user object
                                        .setValue(user)
                                    //now check if the data has been inserted into the data base or not
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(RegisterUser.this, "User has been registered Successfully", Toast.LENGTH_LONG).show();
                                                    progressBarRegister.setVisibility(View.GONE);

                                                    // redirect to login

                                                }
                                                else{
                                                    Toast.makeText(RegisterUser.this, "Failed to register the user", Toast.LENGTH_SHORT).show();
                                                    progressBarRegister.setVisibility(View.GONE);
                                                }
                                            }
                                        });

                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register the user", Toast.LENGTH_SHORT).show();
                            progressBarRegister.setVisibility(View.GONE);
                        }
                    }
                });

    }
}