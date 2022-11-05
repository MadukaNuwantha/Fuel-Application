package com.sliit.fuelapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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


        redirectToUserRegBtn.setOnClickListener(v -> startActivity(new Intent(UserLogin.this, UserRegistration.class)));

        loginBtn.setOnClickListener(v -> {
            email = userEmail.getText().toString();
            password = userPassword.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                userLogin();
            } else {
                Toast.makeText(UserLogin.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userLogin() {
        String URL = "https://fuelapplication.herokuapp.com/api/user/login";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
                try {
                    String ResponseStatus = response.getString("status");


                    if (ResponseStatus.equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("result");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor User = sharedPreferences.edit();

                        String UserID = jsonObject.getString("_id");
                        String Name = jsonObject.getString("name");
                        String Email = jsonObject.getString("email");
                        String VehicleNumber = jsonObject.getString("vehicleNumber");
                        String VehicleType = jsonObject.getString("vehicleType");

                        User.putString("userId", UserID);
                        User.putString("name", Name);
                        User.putString("email", Email);
                        User.putString("vehicleNumber", VehicleNumber);
                        User.putString("vehicleType", VehicleType);
                        User.apply();

                        startActivity(new Intent(UserLogin.this, UserProfile.class));
                    } else {
                        Toast.makeText(UserLogin.this, "Login credentials are incorrect", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            });
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException ignored) {
        }
    }
}

