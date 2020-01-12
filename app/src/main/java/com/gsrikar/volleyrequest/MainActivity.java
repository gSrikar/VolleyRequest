package com.gsrikar.volleyrequest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    /**
     * Logcat tag
     */
    public static final String TAG = MainActivity.class.getSimpleName();

    public static final String URL_USER = "https://reqres.in/api/users/2";

    // UI elements
    private ConstraintLayout mainConstraintLayout;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI elements
        mainConstraintLayout = findViewById(R.id.mainConstraintLayout);
        responseTextView = findViewById(R.id.responseTextView);

        fetchUser();
    }

    private void fetchUser() {
        final StringRequest stringRequest = new StringRequest(URL_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                responseTextView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Snackbar.make(mainConstraintLayout, "Error: " + error.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        // Make the request
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
