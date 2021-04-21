package com.hekapoo.badgekeeper.modules.database_module;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hekapoo.badgekeeper.modules.user_module.UserSchema;

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

    public interface registerCallBack {
        void onComplete(Boolean value);
    }

    public void registerNewUser(String email, String password, UserSchema schema, registerCallBack result) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(schema).addOnCompleteListener(task1 -> {

                    if (task1.isSuccessful()){
                        result.onComplete(task1.isSuccessful());
                    }
                    else
                        Log.d("FB", "toDB_STORE: " + task1.getException().getMessage());
                });

            } else
                Log.d("FB", "toUSER_REGISTER: " + task.getException().getMessage());
        });
    }
}
