package com.hekapoo.badgekeeper.modules.utils_module;

public class UserSchema {

    public String email,department,localization,cardID,cardNumber;

    public UserSchema(String email, String department, String localization, String cardID, String cardNumber) {
        this.email = email;
        this.department = department;
        this.localization = localization;
        this.cardID = cardID;
        this.cardNumber = cardNumber;
    }

    public UserSchema(){}

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getLocalization() {
        return localization;
    }

    public String getCardID() {
        return cardID;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
