package com.gsrikar.volleyrequest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    /**
     * Logcat tag
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String URL_USER = "https://reqres.in/api/users/2";

    // UI elements
    private CoordinatorLayout mainConstraintLayout;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI elements
        mainConstraintLayout = findViewById(R.id.coordinatorLayout);
        responseTextView = findViewById(R.id.responseTextView);

        fetchUser();
    }

    private void fetchUser() {
        // Build the request
        final StringRequest stringRequest = new StringRequest(URL_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(@NonNull String response) {
                // Api call successful
                Log.d(TAG, "Response: " + response);
                // Show the response on the screen
                responseTextView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(@NonNull VolleyError error) {
                // Api call failed
                // Print the error to stacktrace
                error.printStackTrace();
                // Show the error to the user
                Snackbar.make(mainConstraintLayout, "Error: " + error.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        // Make the request
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
