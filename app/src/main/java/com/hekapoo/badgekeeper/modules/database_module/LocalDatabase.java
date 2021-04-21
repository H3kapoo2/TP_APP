package com.hekapoo.badgekeeper.modules.database_module;

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
