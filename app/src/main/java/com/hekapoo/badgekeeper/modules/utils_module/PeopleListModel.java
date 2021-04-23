package com.hekapoo.badgekeeper.modules.utils_module;

public class PeopleListModel {

    private String username,lastUsedTime,checkedInAt,leftToWork;

    public PeopleListModel(String username, String lastUsedTime, String checkedInAt, String leftToWork) {
        this.username = username;
        this.lastUsedTime = lastUsedTime;
        this.checkedInAt = checkedInAt;
        this.leftToWork = leftToWork;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(String lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public String getCheckedInAt() {
        return checkedInAt;
    }

    public void setCheckedInAt(String checkedInAt) {
        this.checkedInAt = checkedInAt;
    }

    public String getLeftToWork() {
        return leftToWork;
    }

    public void setLeftToWork(String leftToWork) {
        this.leftToWork = leftToWork;
    }
}
