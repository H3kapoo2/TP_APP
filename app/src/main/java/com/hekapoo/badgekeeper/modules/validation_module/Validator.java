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

    //validate password syntax at user register, push ERROR to errorTV
    public boolean password(String password, String confirmPassword, TextView errorTV) {

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

    public interface callback{
        void emailVerif(Boolean result);
    }

    //precheck if email already exists in DB, method its async, push ERROR to errorTV
    //validate email syntax at user register, push ERROR to errorTV
    public void emailAsync(String email, TextView errorTV,callback valid) {

        if (email.isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Introduce an email!");
            valid.emailVerif(false);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Not a valid email pattern!");
            valid.emailVerif(false);
            return;
        }

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            //check if the email is not in the DB
            if (task.getResult().getSignInMethods().isEmpty())
                valid.emailVerif(true);
            else {
                errorTV.setVisibility(View.VISIBLE);
                errorTV.setText("Email already exists!");
                valid.emailVerif(false);
            }

        });
    }
}
