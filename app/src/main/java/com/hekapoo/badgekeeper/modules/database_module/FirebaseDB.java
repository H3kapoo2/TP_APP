package com.hekapoo.badgekeeper.modules.database_module;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;

import java.util.HashMap;
import java.util.Map;

/*
 * Class handling firebase database manipulation
 */
public class FirebaseDB {

    private static FirebaseDB instance = null;

    private FirebaseDB() {
    }

    public static FirebaseDB getInstance() {
        if (instance == null)
            instance = new FirebaseDB();
        return instance;
    }

    //boolean callback
    public interface callback {
        void onComplete(Boolean value);
    }

    //userSchema callback
    public interface callback2 {
        void onComplete(UserSchema value);
    }

    //save to fb user modifications about their profile
    public void saveUserChanged(UserSchema user){
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task1 -> {

            if (task1.isSuccessful())
                Log.d("FB", "toDB_STORE: ok ");
            else
                Log.d("FB", "toDB_STORE: " + task1.getException().getMessage());
        });
    }

    public void sendRecoveryEmail(String email,callback result){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            result.onComplete(task.isSuccessful());
        });
    }

    //register and save data to fb
    public void registerNewUser(String email, String password, UserSchema schema, callback result) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(schema).addOnCompleteListener(task1 -> {

                    if (task1.isSuccessful()) {
                        result.onComplete(task1.isSuccessful());
                    } else
                        Log.d("FB", "toDB_STORE: " + task1.getException().getMessage());
                });

            } else
                Log.d("FB", "toUSER_REGISTER: " + task.getException().getMessage());
        });
    }

    //true if email corresponds to a user
    //false otherwise
    public void emailInUse(String email, callback result) {

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            //check if the email is not in the DB
            if (task.getResult().getSignInMethods().isEmpty())
                result.onComplete(false);
            else
                result.onComplete(true);
        });
    }

    public void loginUser(String email, String password, callback result) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            result.onComplete(task.isSuccessful());
        });
    }

    //load user data to fb
    public void getUserFirebase(callback2 result) {

        DatabaseReference firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase.child("Users").child(userID).get().addOnCompleteListener(task -> {

            Map<String, String> userMap;

            if (task.isSuccessful()) {
                userMap = (Map<String, String>) task.getResult().getValue();

                if (!userMap.isEmpty()) {
                    result.onComplete(new UserSchema(
                            userMap.get("pass"),
                            userMap.get("email"),
                            userMap.get("department"),
                            userMap.get("localization"),
                            userMap.get("cardID"),
                            userMap.get("cardNumber"),
                            userMap.get("workHours")));
                    Log.d("firebase_read_data", userMap.toString());
                }
            } else {
                result.onComplete(null);
                Log.e("firebase_read_data", "Error getting data", task.getException());
            }
        });
    }

    //sign out user
    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }
}
