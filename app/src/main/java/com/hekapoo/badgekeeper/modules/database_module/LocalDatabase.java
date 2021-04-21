package com.hekapoo.badgekeeper.modules.database_module;

import android.content.Context;
import android.content.SharedPreferences;

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

    public boolean getLocalSettings(DatabaseEnums type) {
        return false;
    } //todo: change from local

    //save locally using preferences
    public void saveUserLocally(UserSchema user, Context ctx){

        SharedPreferences localDB = ctx.getSharedPreferences("USER_LOCAL", 0); // 0 - for private mode
        SharedPreferences.Editor editor = localDB.edit();

        editor.putString("user_email",user.getEmail());
        editor.putString("user_localization",user.getLocalization());
        editor.putString("user_department",user.getDepartment());
        editor.putString("user_cardID",user.getCardID());
        editor.putString("user_cardNumber",user.getCardNumber());

        editor.commit();
    }

    //retrieve locally saved user using preferences
    public UserSchema loadUserLocally(Context ctx){

        SharedPreferences localDB = ctx.getSharedPreferences("USER_LOCAL", 0); // 0 - for private mode

        String email = localDB.getString("user_email","");
        String localization = localDB.getString("user_localization","");
        String department = localDB.getString("user_department","");
        String cardID = localDB.getString("user_cardID","");
        String cardNumber = localDB.getString("user_cardNumber","");

        UserSchema user = new UserSchema(email,localization,department,cardID,cardNumber);

        if(ValidatorCore.getInstance().userLocallyLoad(user))
            return user;
        else return null;
    }

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
}
