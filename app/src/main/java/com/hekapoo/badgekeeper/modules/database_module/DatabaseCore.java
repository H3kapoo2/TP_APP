package com.hekapoo.badgekeeper.modules.database_module;

/*
* Main class handling database related duties.
*/
public class DatabaseCore {

    private static DatabaseCore instance = null;

    private DatabaseCore() { }

    public static DatabaseCore getInstance()
    {
        if (instance == null)
            instance = new DatabaseCore();

        return instance;
    }


}
