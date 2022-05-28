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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//2->implements View.OnClickListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //5
    private TextView register;
    private EditText editTextEmail,editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    //5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        register =(TextView) findViewById(R.id.textViewRegister);
        register.setOnClickListener(this);  //  1


        //6
        signIn = (Button) findViewById(R.id.buttonLogin);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);


    }

//    3->auto import
    @Override
    public void onClick(View v) {
        switch (v.getId()){  //  4
            case R.id.textViewRegister:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            //7
            case R.id.buttonLogin:
                userLogin();
                break;

        }
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password Is Required");
            editTextPassword.requestFocus();
            return;

        }

        if(password.length()<6){
            editTextPassword.setError("Min password length is 6 characters !");
            editTextPassword.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
//                    redirect to user profile
                    startActivity(new Intent(MainActivity.this,Profile.class));

                }else{
                    Toast.makeText(MainActivity.this, "Failed to login !Please check your cred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}