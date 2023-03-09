package com.example.myapplication.fragment.LoginAndRegister;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.fragment.MainFragment;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    public static final int REQUEST_CODE = 129;
    private TextView tv_fogotPassword;
    private EditText ed_Username, ed_Password;
    private SwitchCompat sc_remember;
    private AppCompatButton btn_login;
    private ImageView img_hidePassword;
    private View viewSnackBar = null;
    private LinearLayout layout_Login, layout_noAccount;

    public LoginFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {

        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewSnackBar = view;
        initUi(view);
        layout_Login.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade));
        registerForOnClick();

//        logout
        if (!MainActivity._logoutAccount) getAccount();
        else setForm();

    }

    private void registerForOnClick() {
        btn_login.setOnClickListener(this);
        sc_remember.setOnClickListener(this);
        tv_fogotPassword.setOnClickListener(this);
        img_hidePassword.setOnClickListener(this);
        layout_noAccount.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String usr = ed_Username.getText().toString();
        String pwd = ed_Password.getText().toString();
        String checkUsername = sharedPreferences.getString("RegUsername", "");
        String checkPassword = sharedPreferences.getString("RegPassword", "");
        switch (view.getId()) {
            case R.id.btn_login:
                if (usr.isEmpty() || pwd.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(viewSnackBar, "Không được để trống tài khoản và mật khẩu", 2000);
                    snackbar.show();
                    break;
                } else {
                    if (usr.equals(checkUsername) && pwd.equals(checkPassword)) {
                        MainActivity.hideSoftKeyboard(getActivity());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
                    } else {
                        Snackbar snackbar = Snackbar.make(viewSnackBar, "Sai tài khoản hoặc mật khẩu", 2000);
                        snackbar.show();
                    }
                }
                break;

            case R.id.img_hidePassword:
                if (ed_Password.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    ed_Password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    img_hidePassword.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                } else {
                    ed_Password.setInputType(REQUEST_CODE);
                    img_hidePassword.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }
                break;
            case R.id.tv_fogotPassword:
                break;
            case R.id.layout_noAccount:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisterFragment()).addToBackStack(null).commit();
                break;

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        saveAccount();
    }

    private void getAccount() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        ed_Username.setText(sharedPreferences.getString("Username", ""));
        ed_Password.setText(sharedPreferences.getString("Password", ""));
        sc_remember.setChecked(sharedPreferences.getBoolean("Remember", false));
    }

    private void saveAccount() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sc_remember.isChecked()) {
            ed_Username.setText("");
            ed_Password.setText("");
        } else {
            editor.putString("Username", ed_Username.getText().toString());
            editor.putString("Password", ed_Password.getText().toString());
            editor.putBoolean("Remember", sc_remember.isChecked());
            editor.apply();
        }

    }

    private void setForm() {
        ed_Username.setText("");
        ed_Password.setText("");
        sc_remember.setChecked(false);
    }

    private void initUi(View view) {
        tv_fogotPassword = view.findViewById(R.id.tv_fogotPassword);
        ed_Username = view.findViewById(R.id.ed_Username);
        ed_Password = view.findViewById(R.id.ed_Password);
        sc_remember = view.findViewById(R.id.sc_remember);
        btn_login = view.findViewById(R.id.btn_login);
        img_hidePassword = view.findViewById(R.id.img_hidePassword);
        layout_Login = view.findViewById(R.id.layout_Login);
        layout_noAccount = view.findViewById(R.id.layout_noAccount);
    }
}