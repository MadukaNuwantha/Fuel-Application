package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserLogin extends AppCompatActivity {
    Button loginBtn;
    TextView redirectToUserRegBtn;
    EditText userEmail, userPassword;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        userEmail = findViewById(R.id.log_userEmail);
        userPassword = findViewById(R.id.log_userPassword);
        loginBtn = findViewById(R.id.log_userLoginBtn);
        redirectToUserRegBtn = findViewById(R.id.redirectToUserRegBtn);


        redirectToUserRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, UserRegistration.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, UserProfile.class));
            }
        });

    }
}