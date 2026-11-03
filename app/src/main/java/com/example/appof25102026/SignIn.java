package com.example.appof25102026;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        EditText inputUsernameSignIn = findViewById(R.id.inputUsernameSignIn);
        EditText inputPasswordSignIn = findViewById(R.id.inputPasswordSignIn);
        TextView noAccSignIn= findViewById(R.id.noAccSignIn);
        Button btnSignIn= findViewById(R.id.btnSignIn);

        String savedUser = getIntent().getStringExtra("username");
        String savedPass = getIntent().getStringExtra("password");

        btnSignIn.setOnClickListener(v -> {
            String user = inputUsernameSignIn.getText().toString().trim();
            String pass = inputPasswordSignIn.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (savedUser == null) {
                Toast.makeText(this, "Please register first", Toast.LENGTH_SHORT).show();
            } else if (user.equals(savedUser) && pass.equals(savedPass)) {
                Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });

        noAccSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}