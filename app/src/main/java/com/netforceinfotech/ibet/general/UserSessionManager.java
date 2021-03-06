package com.netforceinfotech.ibet.general;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManager {

    private static final String R_TOKEN = "rtoken";
    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String REG_ID = "regId";
    private static final String TOKEN = "token";
    private static final String FBID = "fbid";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String ISFIRSTTIME = "first_time";

    private static final String THEME = "theme";

    private static final String MUTEALL = "muteall";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String APITOKEN = "api_token";
    private static final String LOGINMODE = "loginmode";
    private static final String PROFILEPIC = "profilepic";
    private static final String REGREG = "regreg";
    private static final String SOUND_ON_OFF = "sound_on_off";
    private static final String BACKGROUND_CROWD = "background_crowd";
    private static final String WINBET = "winbet";
    private static final String LOSEBET = "losebet";
    private static final String BACKGROUND = "background";
    private static final String COINS = "coins";
    private static final String LANGUAGE = "language";
    private static final String ISLOGGEDIN = "isloggedin";
    private static final String REMOVEADD = "removeads";
    // Shared Preferences reference
    SharedPreferences pref;
    // Editor reference for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    // Constructor
    public UserSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getRegId() {
        return pref.getString(REG_ID, "");
    }

    public void setRegId(String regid) {
        editor.putString(REG_ID, regid);
        editor.commit();
    }

    public void setTeamNotification(String team_name, String sound) {
        editor.putString(team_name, sound);
        editor.commit();

    }

    public boolean getIsFirstTime() {
        return pref.getBoolean(ISFIRSTTIME, true);
    }

    public void setIsFirstTime(boolean regid) {
        editor.putBoolean(ISFIRSTTIME, regid);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(TOKEN, "");
    }

    public void setToken(String regid) {
        editor.putString(TOKEN, regid);
        editor.commit();
    }

    public String getFBID() {

        return pref.getString(FBID, "");
    }

    public void setFBID(String regid) {
        editor.putString(FBID, regid);
        editor.commit();
    }

    public boolean getMuteAllNotification() {

        return pref.getBoolean(MUTEALL, false);
    }


    public void setMuteAllNotification(boolean mute) {

        editor.putBoolean(MUTEALL, mute);
        editor.commit();

    }

    public boolean getTeamNotification(String team_name) {
        return pref.getBoolean(team_name, false);

    }

    public void setTeamNotification(String team_name, boolean sound) {
        editor.putBoolean(team_name, sound);
        editor.commit();
    }


    public int getTheme() {

        return pref.getInt(THEME, 0);
    }

    public void setTheme(int theme) {
        editor.putInt(THEME, theme);
        editor.commit();
    }

    public String getEmail() {

        return pref.getString(EMAIL, "");
    }

    public void setEmail(String regid) {
        editor.putString(EMAIL, regid);
        editor.commit();
    }

    public String getName() {

        return pref.getString(NAME, "Android");
    }

    public void setName(String regid) {
        editor.putString(NAME, regid);
        editor.commit();
    }


    public void setCustomerId(String customer_id) {
        editor.putString(CUSTOMER_ID, customer_id);
        editor.commit();
    }

    public String getCustomerId() {

        return pref.getString(CUSTOMER_ID, "");
    }

    public void setApitoken(String customer_id) {
        editor.putString(APITOKEN, customer_id);
        editor.commit();
    }

    public String getApitoken() {

        return pref.getString(APITOKEN, "DLhRgpl372eKkR1o7WzSDn3SlGntcDVQMTWn9HkrTaRwdFWVhveFfaH7K4QP");
    }

    public String getLoginMode() {
        return pref.getString(LOGINMODE, "0");
    }

    public void setLoginMode(String loginMode) {
        editor.putString(LOGINMODE, loginMode);
        editor.commit();
    }

    public String getProfilePic() {
        return pref.getString(PROFILEPIC, "");
    }

    public void setProfilePic(String loginMode) {
        editor.putString(PROFILEPIC, loginMode);
        editor.commit();
    }


    public void setGCMRegistered(boolean b) {
        editor.putBoolean(REGREG, b);
        editor.commit();
    }

    public boolean getGeneralNotification(String name) {
        return pref.getBoolean(name, true);
    }

    public void setGeneralNotification(String name, boolean b) {
        editor.putBoolean(name, b);
        editor.commit();
    }

    public String getGeneralNotificationFileName(String name) {
        return pref.getString(name, "a_tone.mp3");
    }

    public void setGeneralNotificationFileName(String name, String filename) {
        editor.putString(name, filename);
        editor.commit();
    }

    public String getGeneralNotificationSoundName(String name) {
        return pref.getString(name, "A Tone");
    }

    public void setGeneralNotificationSoundName(String event_name, String name) {
        editor.putString(event_name, name);
        editor.commit();
    }

    public String getTeamNotificationFileName(String name) {
        return pref.getString(name, "a_tone.mp3");
    }

    public void setTeamNotificationFileName(String name, String filename) {
        editor.putString(name, filename);
        editor.commit();
    }

    public String getTeamNotificationSoundName(String name) {
        return pref.getString(name, "A Tone");
    }

    public void setTeamNotificationSoundName(String team_name, String name) {
        editor.putString(team_name, name);
        editor.commit();
    }


    public void setSoundOnOff(boolean b) {
        editor.putBoolean(SOUND_ON_OFF, b);
        editor.commit();
    }

    public boolean getSoundOnOff() {
        return pref.getBoolean(SOUND_ON_OFF, true);
    }

    public void setBackgroundCrowd(boolean b) {
        editor.putBoolean(BACKGROUND_CROWD, b);
        editor.commit();
    }

    public boolean getBackgroundCrowd() {
        return pref.getBoolean(BACKGROUND_CROWD, true);
    }

    public void setWinBet(boolean b) {
        editor.putBoolean(WINBET, b);
        editor.commit();
    }

    public boolean getWinBet() {
        return pref.getBoolean(WINBET, true);
    }

    public void setLoseBet(boolean b) {
        editor.putBoolean(LOSEBET, b);
        editor.commit();
    }

    public boolean getLoseBet() {
        return pref.getBoolean(LOSEBET, true);
    }

    public void setBackground(int choice) {
        editor.putInt(BACKGROUND, choice);
        editor.commit();
    }

    public int getBackground() {
        return pref.getInt(BACKGROUND, 5);
    }

    public void setCoins(String coins) {
        editor.putString(COINS, coins);
        editor.commit();
    }

    public String getCoins() {
        return pref.getString(COINS, "0");
    }

    public void setIsLoggedIn(boolean b) {
        editor.putBoolean(ISLOGGEDIN, b);
        editor.commit();
    }

    public boolean getIsLoogedIn() {
        return pref.getBoolean(ISLOGGEDIN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }


    public void setlanguage(String code) {
        editor.putString(LANGUAGE, code);
        editor.commit();
    }

    public String getLanguage() {
        return pref.getString(LANGUAGE, "en");
    }

    public void removeAds(boolean b) {
        editor.putBoolean(REMOVEADD, b);
        editor.commit();
    }
    public boolean getRemoveAds(){
        return pref.getBoolean(REMOVEADD, false);
    }
}