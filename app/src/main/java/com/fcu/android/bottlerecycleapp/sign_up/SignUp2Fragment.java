package com.fcu.android.bottlerecycleapp.sign_up;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.User;

public class SignUp2Fragment extends Fragment {

    private User user;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up2, container, false);

        // Get passed User object from arguments
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
        }

        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);

        view.findViewById(R.id.btn_sing_up_next_page).setOnClickListener(v -> {
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                etPassword.setError("Password must contain at least one lowercase letter, one uppercase letter, one number, and be 8-20 characters long");
            } else if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords do not match");
            } else {
                // Passwords are valid, set it in the User object
                user.setPassword(password);

                // Proceed to the next step or complete the sign-up process
                // For example, you could send user data to server or another Fragment
                Toast.makeText(getActivity(), "Sign-up successful", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
