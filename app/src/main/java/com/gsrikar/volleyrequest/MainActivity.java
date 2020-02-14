package com.gsrikar.volleyrequest;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    /**
     * Logcat tag
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String URL_USER = "https://reqres.in/api/users/2";

    // UI elements
    private CoordinatorLayout mainConstraintLayout;
    private TextView responseTextView;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI elements
        mainConstraintLayout = findViewById(R.id.coordinatorLayout);
        responseTextView = findViewById(R.id.responseTextView);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab = findViewById(R.id.fab);

        setListener();
        fetchUser();
    }

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnack(getString(R.string.clicked_button_fab));
            }
        });
        bottomAppBar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item clicks
                final int itemId = item.getItemId();
                if (itemId == R.id.settings) {
                    showSnack(getString(R.string.clicked_menu_settings));
                }
                return true;
            }
        });
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the navigation clicks
                showSnack(getString(R.string.clicked_menu_nav));
            }
        });
    }

    private void showSnack(@NonNull final String message) {
        Snackbar.make(mainConstraintLayout, message, LENGTH_SHORT)
                .setAnchorView(fab)
                .show();
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
                showSnack(error.getMessage() != null ? error.getMessage() : getString(R.string.error_api_call_failed));
            }
        });

        // Make the request
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
