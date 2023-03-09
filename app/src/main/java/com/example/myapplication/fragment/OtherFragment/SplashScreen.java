package com.example.myapplication.fragment.OtherFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragment.LoginAndRegister.LoginFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashScreen extends Fragment {


    public SplashScreen() {
        // Required empty public constructor
    }


    public static SplashScreen newInstance() {
        return new SplashScreen();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goToMainAndFinish();
    }
    private void goToMainAndFinish()
    {
        new CountDownTimer(1000,1500){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
            }
        }.start();
    }

}