package com.hekapoo.badgekeeper.modules.utils_module;

public class SettingsSchema {
    private String language;
    private boolean fingerprintLogin,keepLogin,notifications;

    public SettingsSchema(String language, boolean fingerprintLogin, boolean keepLogin, boolean notifications) {
        this.language = language;
        this.fingerprintLogin = fingerprintLogin;
        this.keepLogin = keepLogin;
        this.notifications = notifications;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isFingerprintLogin() {
        return fingerprintLogin;
    }

    public void setFingerprintLogin(boolean fingerprintLogin) {
        this.fingerprintLogin = fingerprintLogin;
    }

    public boolean isKeepLogin() {
        return keepLogin;
    }

    public void setKeepLogin(boolean keepLogin) {
        this.keepLogin = keepLogin;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
