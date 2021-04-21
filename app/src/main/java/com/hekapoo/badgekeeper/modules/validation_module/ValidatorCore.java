package com.hekapoo.badgekeeper.modules.validation_module;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;

import org.w3c.dom.Text;

public class ValidatorCore {
    private static ValidatorCore instance = null;

    private ValidatorCore() {
    }

    public static ValidatorCore getInstance() {
        if (instance == null)
            instance = new ValidatorCore();
        return instance;
    }

    public interface callback {
        void onComplete(Boolean result);
    }

    //validate login credentials
    public boolean login(String password, String email, TextView errorTV) {

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
        return password(password, password, errorTV);

    }

    //validate password syntax, push ERROR to errorTV
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

    //validate email and passwords async,push ERROR to errorTV
    public void emailAndPasswordsAsync(String email, String password, String confirmPass, TextView errorTV, callback valid) {

        if (email.isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Introduce an email!");
            valid.onComplete(false);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Not a valid email pattern!");
            valid.onComplete(false);
            return;
        }

        FirebaseDB.getInstance().emailInUse(email, inUse -> {
            if (inUse) {
                errorTV.setVisibility(View.VISIBLE);
                errorTV.setText("Email already exists!");
                valid.onComplete(false);
            } else
                valid.onComplete(password(password, confirmPass, errorTV));
        });
    }

    //validate data in UserSchema and put ERROR in errorTV
    public boolean userSchema(UserSchema schema, TextView errorTV) {

        //localization validation
        if (schema.getLocalization().isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Please select a localization!");
            return false;
        }

        //department validation
        if (schema.getDepartment().isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Please select a department!");
            return false;
        }
        //card id validation
        if (schema.getCardID().isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Please introduce the card ID");
            return false;
        }

        if (schema.getCardID().length() < 4) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Card ID too short!");
            return false;
        }

        //card number validation
        if (schema.getCardNumber().isEmpty()) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Please introduce the card number!");
            return false;
        }

        if (schema.getCardNumber().length() < 7) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText("Card Number too short!");
            return false;
        }

        return true;
    }

    //validate user locally loaded data
    public boolean userLocallyLoad(UserSchema user){
        if(user.getEmail().isEmpty() ||
                user.getLocalization().isEmpty() ||
                user.getDepartment().isEmpty() ||
                user.getCardID().isEmpty() ||
                user.getCardNumber().isEmpty())
            return false;
        return true;
    }
}
