package com.hekapoo.badgekeeper.modules.database_module;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.biometric.BiometricManager;

import com.hekapoo.badgekeeper.modules.utils_module.SettingsSchema;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;
import com.hekapoo.badgekeeper.modules.validation_module.ValidatorCore;

/*
 * Class handling local database manipulation
 */
public class LocalDatabase {

    private static LocalDatabase instance = null;

    private LocalDatabase() {
    }

    public static LocalDatabase getInstance() {
        if (instance == null)
            instance = new LocalDatabase();
        return instance;
    }

    public boolean isInit(Context ctx) {
        SharedPreferences localDB = ctx.getSharedPreferences("APP_SETTINGS", 0); // 0 - for private mode

        return localDB.getBoolean("app_init", false);
    }

    public void firstTimeLocalSetup(Context ctx) {
        SharedPreferences localDB = ctx.getSharedPreferences("APP_SETTINGS", 0); // 0 - for private mode
        SharedPreferences.Editor editor = localDB.edit();

        editor.putBoolean("app_fingerprint", false);
        editor.putBoolean("app_keeplogin", false);
        editor.putBoolean("app_notifs", true);
        editor.putString("app_language", "English");

        //todo: CHECK HERE FOR FINGERPRINT AVAILABLE AND SET IT
        BiometricManager biometricManager = BiometricManager.from(ctx);

        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS: //proceed
                editor.putBoolean("app_print_avail", true);
            default:
                editor.putBoolean("app_print_avail", false);

        }

        editor.putBoolean("app_init", true);


        editor.commit();
    }

    public SettingsSchema getLocalSettings(Context ctx) {
        SharedPreferences localDB = ctx.getSharedPreferences("APP_SETTINGS", 0); // 0 - for private mode

        boolean isFingerprint = localDB.getBoolean("app_fingerprint", false);
        boolean isKeepLogin = localDB.getBoolean("app_keeplogin", false);
        boolean isNotifications = localDB.getBoolean("app_notifs", false);
        boolean mutedFingerprint = localDB.getBoolean("app_print_avail", true);
        String language = localDB.getString("app_language", "");

        return new SettingsSchema(language, isFingerprint, isKeepLogin, isNotifications, mutedFingerprint);

    }

    public void saveLocalSettings(SettingsSchema settings, Context ctx) {
        SharedPreferences localDB = ctx.getSharedPreferences("APP_SETTINGS", 0); // 0 - for private mode
        SharedPreferences.Editor editor = localDB.edit();

        editor.putBoolean("app_fingerprint", settings.isFingerprintLogin());
        editor.putBoolean("app_keeplogin", settings.isKeepLogin());
        editor.putBoolean("app_notifs", settings.isNotifications());
        editor.putString("app_language", settings.getLanguage());

        editor.commit();
    }

    //save locally using preferences
    public void saveUserLocally(UserSchema user, Context ctx) {
        SharedPreferences localDB = ctx.getSharedPreferences("USER_LOCAL", 0); // 0 - for private mode
        SharedPreferences.Editor editor = localDB.edit();

        editor.putString("user_password", user.getPassword());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_localization", user.getLocalization());
        editor.putString("user_department", user.getDepartment());
        editor.putString("user_cardID", user.getCardID());
        editor.putString("user_cardNumber", user.getCardNumber());
        editor.putString("user_workHours", user.getWorkHours());
        editor.commit();
    }

    //retrieve locally saved user using preferences
    public UserSchema loadUserLocally(Context ctx) {

        SharedPreferences localDB = ctx.getSharedPreferences("USER_LOCAL", 0); // 0 - for private mode

        String password = localDB.getString("user_password", "");
        String email = localDB.getString("user_email", "");
        String localization = localDB.getString("user_localization", "");
        String department = localDB.getString("user_department", "");
        String cardID = localDB.getString("user_cardID", "");
        String cardNumber = localDB.getString("user_cardNumber", "");
        String workHours = localDB.getString("user_workHours", "");

        UserSchema user = new UserSchema(password, email, department, localization, cardID, cardNumber, workHours);

        if (ValidatorCore.getInstance().userLocallyLoad(user))
            return user;
        else return null;
    }

    //save user settings locally
    //retrieve user settings


    //TODO: move those to to Firebase for retrieval
    public String[] getLocalizationArray() {
        String[] values = new String[]{
                "Timisoara, Romania",
                "Bucuresti, Romania",
                "Brasov, Romania",
                "Baia Mare, Romania",
                "Timisoara, Romania",
                "Timisoara, Romania",
                "Timisoara, Romania",
                "Timisoara, Romania",
                "Timisoara, Romania",
                "Timisoara, Romania",
                "Bucuresti, Romania",
                "Brasov, Romania",
                "Baia Mare, Romania",
                "Timisoara, Romania",
        };

        return values;
    }

    public String[] getDepartmentArray() {
        String[] values = new String[]{
                "SWM",
                "OAM",
                "SWM",
                "DEPT2",
                "SWM",
                "RAMP",
                "SWM",
                "OAM",
                "CLID",
                "EODEC",
        };

        return values;
    }

    public String[] getWorkHoursArray() {
        String[] values = new String[]{
                "4h 0m",
                "4h 30m",
                "5h 0m",
                "5h 30m",
                "6h 0m",
                "6h 30m",
                "7h 0m",
                "7h 30m",
                "8h 0m",
        };
        return values;
    }

    public String[] getLanguageArray() {
        String[] values = new String[]{
                "Romana",
                "English",
        };
        return values;
    }
}
