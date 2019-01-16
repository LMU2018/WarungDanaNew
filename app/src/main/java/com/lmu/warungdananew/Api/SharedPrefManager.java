package com.lmu.warungdananew.Api;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPrefManager {
    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";
    public static final String SP_NPM = "spNpm";
    public static final String SP_ID = "spId";
    public static final String SP_NAME = "spName";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    public static final String SP_ROLES = "spRole";
    public static final String SP_ROLES_NAMES = "spRoleNames";
    public static final String SP_OUTLET_ID = "spOutletId";
    public static final String SP_OUTLET_NAME = "spOutletName";
    public static final String SP_LAST_LOGIN = "spLastLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "introslider";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPLong(String keySP, long value) {
        spEditor.putLong(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void saveSPArray(String keySP, Set<String> set) {
        spEditor.putStringSet(keySP, set);
        spEditor.commit();
    }

    public String getSPNpm() {
        return sp.getString(SP_NPM, "");
    }

    public String getSPRole() {
        return sp.getString(SP_ROLES_NAMES, "");
    }

    public Integer getSpId() {
        return sp.getInt(SP_ID, 0);
    }

    public Integer getSpIdRole() {
        return sp.getInt(SP_ROLES, 0);
    }

    public Integer getSpOutletId() {
        return sp.getInt(SP_OUTLET_ID, 0);
    }

    public String getSPName() {
        return sp.getString(SP_NAME, "");
    }

    public String getSPOutletName() {
        return sp.getString(SP_OUTLET_NAME, "");
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        spEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        spEditor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sp.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public Integer getSpLastLogin() {
        return sp.getInt(SP_LAST_LOGIN, 0);
    }

}
