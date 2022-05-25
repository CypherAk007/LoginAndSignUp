package com.example.customloginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//2->implements View.OnClickListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register =(TextView) findViewById(R.id.textViewRegister);
        register.setOnClickListener(this);  //  1
    }

//    3->auto import
    @Override
    public void onClick(View v) {
        switch (v.getId()){  //  4
            case R.id.textViewRegister:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }
}