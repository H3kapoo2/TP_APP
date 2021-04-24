package com.hekapoo.badgekeeper.modules.utils_module;

public class UserSchema {

    public String email,department,localization,cardID,cardNumber,workHours,password;

    public String checkInAt,lastUsedBadge,leftToWork;

    public UserSchema(String password,String email, String department, String localization, String cardID, String cardNumber,String workHours) {
        this.email = email;
        this.department = department;
        this.localization = localization;
        this.cardID = cardID;
        this.cardNumber = cardNumber;
        this.workHours = workHours;
        this.password = password;
    }

    public UserSchema(){}

    public String getCheckInAt() {
        return checkInAt;
    }

    public void setCheckInAt(String checkInAt) {
        this.checkInAt = checkInAt;
    }

    public String getLastUsedBadge() {
        return lastUsedBadge;
    }

    public void setLastUsedBadge(String lastUsedBadge) {
        this.lastUsedBadge = lastUsedBadge;
    }

    public String getLeftToWork() {
        return leftToWork;
    }

    public void setLeftToWork(String leftToWork) {
        this.leftToWork = leftToWork;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
