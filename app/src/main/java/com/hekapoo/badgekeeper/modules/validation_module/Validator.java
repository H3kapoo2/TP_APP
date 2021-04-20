package com.hekapoo.badgekeeper.modules.validation_module;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Validator {
    private static Validator instance = null;

    private Validator() {
    }

    public static Validator getInstance() {
        if (instance == null)
            instance = new Validator();
        return instance;
    }

    //validate password at user register, push ERROR to errorTV
    public Boolean password(String password, String confirmPassword, TextView errorTV) {

        Log.d("pass", "password0: ");


        if (password.isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Introduce a password!");
            Log.d("pass", "password1: ");
            return false;
        }

        if (password.length() < 6) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Password too short!");
            Log.d("pass", "password12: ");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Passwords don't match!");
            Log.d("pass", "password123: ");
            return false;
        }

        return true;
    }

    interface emailValidation{
        void emailValid(Boolean result);
    }

    //validate email at user register, push ERROR to errorTV
    public void preCheckEmailExistsAlready(String email, TextView errorTV,emailValidation valid) {

        //TODO: EMAIL VERIFICATION ASYNC

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            //check if the email is not in the DB
            if (task.getResult().getSignInMethods().isEmpty())
            {
                valid.emailValid(true);
            }
            else {
                errorTV.setVisibility(View.VISIBLE);
                errorTV.setText("Email already exists!");
            }

        });

    }

    public Boolean email(String email, TextView errorTV,emailValidation valid) {

        if (email.isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Introduce an email!");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Not a valid email pattern!");
            return false;
        }

        return true;
    }



    //other validators
}
