package com.hekapoo.badgekeeper.modules.database_module;

/*
 * Class handling local database manipulation
 */
public class LocalDatabase {

    private static LocalDatabase instance = null;

    private LocalDatabase() { }

    public static LocalDatabase getInstance()
    {
        if (instance == null)
            instance = new LocalDatabase();
        return instance;
    }

    public boolean getLocalSettings(DatabaseEnums type){
        return false;
    } //todo: change from local
}
