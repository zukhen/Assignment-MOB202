package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.ClipboardManager;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.example.myapplication.fragment.OtherFragment.SplashScreen;

import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {
    public static boolean _logoutAccount;
    public static ClipboardManager _myClipboard;
    public static  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SplashScreen()).commit();
        _myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        View view = activity.getCurrentFocus();
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }


}