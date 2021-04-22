package com.hekapoo.badgekeeper.modules.utils_module;

/*
 * Class to manage basic string parsing
 * */
public class TextParser {
    private static TextParser instance = null;

    private TextParser() {
    }

    public static TextParser getInstance() {
        if (instance == null)
            instance = new TextParser();

        return instance;
    }

    public String parseProfileUsername(String email) {

        //todo: parse differently if there's a dash in the email
        String firstPart = email.split("@")[0];
        String firstName = firstPart.split("\\.")[0];
        String lastName = firstPart.split("\\.")[1];

        String firstNameUpper = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        String lastNameUpper = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        return firstNameUpper + " " + lastNameUpper;

    }

    public String[] parseDashboardName(String email) {
        String firstPart = email.split("@")[0];
        String firstName = firstPart.split("\\.")[0].toUpperCase();
        String lastName = firstPart.split("\\.")[1].toUpperCase();

        return new String[]{firstName, lastName};
    }

}
