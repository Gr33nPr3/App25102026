package com.example.appof25102026;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        EditText etUsernameSignUp = findViewById(R.id.etUsernameSignUp);
        EditText etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        EditText etConfirmPasswordSignUp = findViewById(R.id.etConfirmPasswordSignUp);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            String user = etUsernameSignUp.getText().toString().trim();
            String pass = etPasswordSignUp.getText().toString().trim();
            String confirm = etConfirmPasswordSignUp.getText().toString().trim();

            if (user.isEmpty()) {
                etUsernameSignUp.setError("Required field");
                return;
            }
            if (pass.length() < 6) {
                etPasswordSignUp.setError("At least 6 characters");
                return;
            }
            if (!pass.equals(confirm)) {
                etConfirmPasswordSignUp.setError("Passwords don't match");
                return;
            }

            Intent intent = new Intent(SignUp.this, SignIn.class);
            intent.putExtra("username", user);
            intent.putExtra("password", pass);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}