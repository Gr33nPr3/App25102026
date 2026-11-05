package com.example.appof25102026;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;

public class SignIn extends AppCompatActivity {
    private MaterialCheckBox cbRememberMe;
    private static final String PREFS_NAME = "login_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember_me";

    private SharedPreferences prefs;
    private EditText inputUsernameSignIn, inputPasswordSignIn;
    private TextView noAccSignIn;
    private Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        inputUsernameSignIn = findViewById(R.id.inputUsernameSignIn);
        inputPasswordSignIn = findViewById(R.id.inputPasswordSignIn);
        noAccSignIn = findViewById(R.id.noAccSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        loadCredentials();


        String savedUser = getIntent().getStringExtra("username");
        String savedPass = getIntent().getStringExtra("password");
        if (savedUser == null) {
            savedUser = prefs.getString(KEY_USERNAME, null);
            savedPass = prefs.getString(KEY_PASSWORD, null);
        }
        final String expectedUser = savedUser;
        final String expectedPass = savedPass;

        btnSignIn.setOnClickListener(v -> {
            String user = inputUsernameSignIn.getText().toString().trim();
            String pass = inputPasswordSignIn.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (expectedUser == null) {
                Toast.makeText(this, "Please register first", Toast.LENGTH_SHORT).show();
            } else if (user.equals(expectedUser) && pass.equals(expectedPass)) {
                if (cbRememberMe.isChecked()) {
                    saveCredentials(user, pass);
                }
                Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });

        noAccSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
            finish();
        });

        cbRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked && prefs.getBoolean(KEY_REMEMBER, false)) {
                showForgetSnackbar();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER, true);
        editor.apply();
    }

    private void loadCredentials() {
        boolean remember = prefs.getBoolean(KEY_REMEMBER, false);

        if (remember) {
            inputUsernameSignIn.setText(prefs.getString(KEY_USERNAME, ""));
            inputPasswordSignIn.setText(prefs.getString(KEY_PASSWORD, ""));
            cbRememberMe.setChecked(true);
        }
    }

    private void showForgetSnackbar() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "Forget the saved login details?",
                Snackbar.LENGTH_LONG);

        snackbar.setAction("Yes, forget", v -> clearCredentials());

        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar sb, int event) {
                if (event != DISMISS_EVENT_ACTION) {
                    cbRememberMe.setChecked(true);
                }
            }
        });

        snackbar.show();
    }

    private void clearCredentials() {
        prefs.edit().clear().apply();
    }
}