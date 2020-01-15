package com.gsrikar.volleyrequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    /**
     * Logcat tag
     */
    private static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * End point to validate the user credentials
     */
    private static final String URL_LOGIN = "https://reqres.in/api/login";

    // UI elements
    private MaterialButton loginButton;
    private TextInputEditText emailTextEditText;
    private TextInputEditText passwordTextEditText;
    private ProgressBar loadingBar;
    private ConstraintLayout loginConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the UI elements
        loginConstraintLayout = findViewById(R.id.loginConstraintLayout);
        loadingBar = findViewById(R.id.loadingBar);
        loginButton = findViewById(R.id.loginButton);
        emailTextEditText = findViewById(R.id.emailTextEditText);
        passwordTextEditText = findViewById(R.id.passwordTextEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailTextEditText.getText().toString(), passwordTextEditText.getText().toString());
            }
        });
    }

    /**
     * Validate the email and password combination
     *
     * @param email    Entered email address
     * @param password Entered password
     */
    private void login(@NonNull final String email, @NonNull final String password) {
        loadingBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Request Payload: " + jsonObject);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_LOGIN,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(@NonNull JSONObject response) {
                try {
                    @NonNull final String token = response.getString("token");
                    Log.d(TAG, "Token: " + token);
                    // Open the Next Screen
                    openMainActivity(email);
                    // Close the current screen
                    finish();
                } catch (JSONException e) {
                    // Show the error
                    e.printStackTrace();
                    loadingBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                    Snackbar.make(loginConstraintLayout, "Error " + e.getMessage(),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(@NonNull VolleyError error) {
                error.printStackTrace();
                loadingBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                Snackbar.make(loginConstraintLayout, "Error " + error.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        // Make the request
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void openMainActivity(@NonNull final String email) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
