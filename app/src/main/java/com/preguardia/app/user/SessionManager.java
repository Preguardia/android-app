package com.preguardia.app.user;

import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import javax.inject.Inject;

/**
 * @author amouly on 4/10/16.
 */
public class SessionManager {

    private TrayAppPreferences appPreferences;

    @Inject
    public SessionManager(TrayAppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    public void logoutUser() {
        appPreferences.clear();
    }

    public String getUserId() {
        return appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
    }

    public void setUserId(String userId) {
        appPreferences.put(Constants.PREFERENCES_USER_UID, userId);
    }

    public String getUserToken() {
        return appPreferences.getString(Constants.PREFERENCES_USER_TOKEN, null);
    }

    public void setUserToken(String userToken) {
        appPreferences.put(Constants.PREFERENCES_USER_TOKEN, userToken);
    }

    public String getUserName() {
        return appPreferences.getString(Constants.PREFERENCES_USER_NAME, null);
    }

    public void setUserName(String userName) {
        appPreferences.put(Constants.PREFERENCES_USER_NAME, userName);
    }

    public String getUserType() {
        return appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);
    }

    public void setUserType(String userType) {
        appPreferences.put(Constants.PREFERENCES_USER_TYPE, userType);
    }


}
