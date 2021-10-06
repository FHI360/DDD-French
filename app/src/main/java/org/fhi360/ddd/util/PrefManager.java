package org.fhi360.ddd.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class PrefManager {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public HashMap<String, String> getIpAddress() {
        HashMap<String, String> user = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("ipAddressDb", Context.MODE_PRIVATE);
        user.put("ipAddress", sharedPreferences.getString("ipAddress", null));
        return user;
    }

    @SuppressLint("ApplySharedPref")
    public void saveIpAddress(String ipAddress) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ipAddressDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ipAddress", ipAddress);
        editor.commit();
    }

    public HashMap<String, String> getDrugs1() {
        HashMap<String, String> user = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("drugDB1", Context.MODE_PRIVATE);
        user.put("name", sharedPreferences.getString("name", null));
        user.put("basicUnit", sharedPreferences.getString("basicUnit", null));
        user.put("balance", sharedPreferences.getString("balance", null));
        user.put("qtyrecieved", sharedPreferences.getString("qtyrecieved", null));
        user.put("dispense", sharedPreferences.getString("dispense", null));
        return user;
    }

    public HashMap<String, String> getDrugs2() {
        HashMap<String, String> user = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("drugDB2", Context.MODE_PRIVATE);
        user.put("name", sharedPreferences.getString("name", null));
        user.put("basicUnit", sharedPreferences.getString("basicUnit", null));
        user.put("balance", sharedPreferences.getString("balance", null));
        user.put("qtyrecieved", sharedPreferences.getString("qtyrecieved", null));
        user.put("dispense", sharedPreferences.getString("dispense", null));
        return user;
    }

    public HashMap<String, String> getDrugs3() {
        HashMap<String, String> user = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("drugDB3", Context.MODE_PRIVATE);
        user.put("name", sharedPreferences.getString("name", null));
        user.put("basicUnit", sharedPreferences.getString("basicUnit", null));
        user.put("balance", sharedPreferences.getString("balance", null));
        user.put("qtyrecieved", sharedPreferences.getString("qtyrecieved", null));
        user.put("dispense", sharedPreferences.getString("dispense", null));
        return user;
    }

    @SuppressLint("ApplySharedPref")
    public void saveDrug1(String name, int basicUnit, int balance, int qtyrecieved, int dispense) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("drugDB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("basicUnit", basicUnit + "");
        editor.putString("balance", balance + "");
        editor.putString("qtyrecieved", qtyrecieved + "");
        editor.putString("dispense", dispense + "");
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void saveDrug2(String name, int basicUnit, int balance, int qtyrecieved, int dispense) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("drugDB2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("basicUnit", basicUnit + "");
        editor.putString("balance", balance + "");
        editor.putString("qtyrecieved", qtyrecieved + "");
        editor.putString("dispense", dispense + "");
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void saveDrug3(String name, int basicUnit, int balance, int qtyrecieved, int dispense) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("drugDB3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("basicUnit", basicUnit + "");
        editor.putString("balance", balance + "");
        editor.putString("qtyrecieved", qtyrecieved + "");
        editor.putString("dispense", dispense + "");
        editor.commit();
    }
}
