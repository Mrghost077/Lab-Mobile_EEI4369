package com.s23010162.safwan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private DBHelper dbHelper;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        loginButton = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        loginButton.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Assuming insertUser returns true if user credentials saved successfully
            boolean inserted = dbHelper.insertUser(username, password);

            if (inserted) {
                Toast.makeText(MainActivity.this, "Saved credentials successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                finish(); // Optional: close current activity
            } else {
                Toast.makeText(MainActivity.this, "Save failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
