package com.sliit.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StationRegistration extends AppCompatActivity {
    TextView redirectToStationLoginBtn;
    EditText stationName, stationEmail, stationPassword;
    Button registerStationBtn;
    String name, email, password;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_registration);

        stationName = findViewById(R.id.reg_stationName);
        stationEmail = findViewById(R.id.reg_stationEmail);
        stationPassword = findViewById(R.id.reg_stationPassword);
        registerStationBtn = findViewById(R.id.reg_stationRegisterBtn);
        redirectToStationLoginBtn = findViewById(R.id.redirectToStationLoginBtn);

        redirectToStationLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StationRegistration.this, StationLogin.class));
            }
        });

        registerStationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = stationName.getText().toString();
                email = stationEmail.getText().toString();
                password = stationPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerStation();
                } else {
                    Toast.makeText(StationRegistration.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerStation() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    stationName.getText().clear();
                    stationEmail.getText().clear();
                    stationPassword.getText().clear();
                    Toast.makeText(StationRegistration.this, "Success!", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}