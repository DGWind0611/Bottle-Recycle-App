package com.fcu.android.bottlerecycleapp.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.User;

import java.io.Serializable;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etEmail;
    private EditText etPhoneNumber;
    private Button btnSignUpNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        etUserName = findViewById(R.id.et_user_name);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnSignUpNext = findViewById(R.id.btn_sing_up_next_page);

        btnSignUpNext.setOnClickListener(v -> {
            String userName = etUserName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            if (userName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Create User object
                User user = new User();
                user.setUserName(userName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);

                // Pass the User object to the next Fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", (Serializable) user);

                // Replace the current activity content with the SignUp2Fragment
                SignUp2Fragment signUp2Fragment = new SignUp2Fragment();
                signUp2Fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, signUp2Fragment)
                        .addToBackStack(null)  // Optional, to allow the user to go back to previous step
                        .commit();
            }
        });
    }
}
