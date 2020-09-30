package com.example.myapplication.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.UUID;

public class SessionManager {
    private SharedPreferences prefs;


    public SessionManager(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).apply();
    }

    public String getUsername() {
        String username = prefs.getString("username","");
        return username;
    }

    public void setToken(String token) {
        prefs.edit().putString("token", token).apply();
    }

    public String getToken() {
        String token = prefs.getString("token","");
        return token;
    }
    public void setId(String id) {
        prefs.edit().putString("id", id).apply();
    }

    public String getId() {
        String id = prefs.getString("id","");
        return id;
    }
     public void setUserId(String userId) {
        prefs.edit().putString("userId", userId).apply();
    }

    public String getUserId() {
        String userId = prefs.getString("userId","");
        return userId;
    }

    public void setRole(String role) {
        prefs.edit().putString("role", role).apply();
    }

    public String getRole() {
        String role = prefs.getString("role","");
        return role;
    }

    public void setBarcode(String barcode) {
        prefs.edit().putString("barcode", barcode).apply();
    }

    public String getBarcode() {
        String barcode = prefs.getString("barcode","");
        return barcode;
    }

    public void setDeviceType(String deviceType) {
        prefs.edit().putString("deviceType", deviceType).apply();
    }

    public String getDeviceType() {
        String deviceType = prefs.getString("deviceType","");
        return deviceType;
    }

    public void setTrackLocation(String trackLocation) {
        prefs.edit().putString("trackLocation", trackLocation).apply();
    }
    public String getTrackLocation() {
        String trackLocation = prefs.getString("trackLocation","");
        return trackLocation;
    }

    public void setAssetItemId(String assetItemId) {
        prefs.edit().putString("assetItemId", assetItemId).apply();
    }
    public String getAssetItemId() {
        String assetItemId = prefs.getString("assetItemId","");
        return assetItemId;
    }

    public void setTrackAssetLocation(String trackAssetLocation) {
        prefs.edit().putString("trackAssetLocation", trackAssetLocation).apply();
    }
    public String getTrackAssetLocation() {
        String trackAssetLocation = prefs.getString("trackAssetLocation","");
        return trackAssetLocation;
    }

    public void setTrackAssetDepartment(String trackAssetDepartment) {
        prefs.edit().putString("trackAssetDepartment", trackAssetDepartment).apply();
    }
    public String getTrackAssetDepartment() {
        String trackAssetDepartment = prefs.getString("trackAssetDepartment","");
        return trackAssetDepartment;
    }

    public void setTrackType(String trackType) {
        prefs.edit().putString("trackType", trackType).apply();
    }
    public String getTrackType() {
        String trackType = prefs.getString("trackType","");
        return trackType;
    }

    public void setTrackBarcode(String trackBarcode) {
        prefs.edit().putString("trackType", trackBarcode).apply();
    }
    public String getTrackBarcode() {
        String trackBarcode = prefs.getString("trackBarcode","");
        return trackBarcode;
    }
}
