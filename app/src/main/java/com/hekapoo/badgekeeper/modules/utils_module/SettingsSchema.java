package com.hekapoo.badgekeeper.modules.utils_module;

public class SettingsSchema {
    private String language;
    private boolean fingerprintLogin,keepLogin,notifications;
    private boolean fingerprintAvailable;

    public SettingsSchema(String language, boolean fingerprintLogin, boolean keepLogin, boolean notifications,boolean fingerprintAvailable) {
        this.language = language;
        this.fingerprintLogin = fingerprintLogin;
        this.keepLogin = keepLogin;
        this.notifications = notifications;
        this.fingerprintAvailable = fingerprintAvailable;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isFingerprintAvailable() {
        return fingerprintAvailable;
    }

    public void setFingerprintAvailable(boolean fingerprintAvailable) {
        this.fingerprintAvailable = fingerprintAvailable;
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
