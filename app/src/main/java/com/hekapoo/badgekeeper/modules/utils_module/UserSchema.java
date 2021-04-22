package com.hekapoo.badgekeeper.modules.utils_module;

public class UserSchema {

    public String email,department,localization,cardID,cardNumber,workHours;

    public UserSchema(String email, String department, String localization, String cardID, String cardNumber,String workHours) {
        this.email = email;
        this.department = department;
        this.localization = localization;
        this.cardID = cardID;
        this.cardNumber = cardNumber;
        this.workHours = workHours;
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

    public String getWorkHours() {
        return workHours;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }
}
