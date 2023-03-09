package com.example.myapplication.Untils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefControl {
    // lưu giá trị tên
    public static void savingFullName(Context context, String firstName) {
        SharedPreferences pre = context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("fullName", firstName);
        editor.apply();
    }

    // lấy giá trị tên
    public static String getPreferences(Context context) {
        SharedPreferences pre = context.getSharedPreferences("Account", 0);
        return pre.getString("fullName", " ");
    }


}
