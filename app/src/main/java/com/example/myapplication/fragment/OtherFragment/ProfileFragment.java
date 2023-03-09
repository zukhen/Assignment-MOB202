package com.example.myapplication.fragment.OtherFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Untils.SharedPrefControl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Dialog dialog;
    private ImageView img_backToMain;
    private AppCompatButton btn_editFullName;
    private TextView tv_fullNameProfile;
    private String url;
    private Bundle bundle;
    private WebViewFragment webviewFragment;
    private LinearLayout layout_changePassword, layout_getHelp, layout_regulation, layout_privacyPolicy, layout_facebook, layout_gitHub;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {

        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
    }


    private void registerForOnclick() {
        layout_changePassword.setOnClickListener(this);
        layout_getHelp.setOnClickListener(this);
        layout_regulation.setOnClickListener(this);
        layout_privacyPolicy.setOnClickListener(this);
        layout_gitHub.setOnClickListener(this);
        btn_editFullName.setOnClickListener(this);
        img_backToMain.setOnClickListener(this);
    }

    private void initUi(View view) {
        btn_editFullName = view.findViewById(R.id.btn_editFullName);
        img_backToMain = view.findViewById(R.id.img_backToMain);
        tv_fullNameProfile = view.findViewById(R.id.tv_fullNameProfile);
        layout_getHelp = view.findViewById(R.id.layout_getHelp);
        layout_regulation = view.findViewById(R.id.layout_regulation);
        layout_privacyPolicy = view.findViewById(R.id.layout_privacyPolicy);
        layout_gitHub = view.findViewById(R.id.layout_gitHub);
        layout_changePassword = view.findViewById(R.id.layout_changePassword);
//        set Text
        tv_fullNameProfile.setText(SharedPrefControl.getPreferences(getContext()));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_backToMain:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.btn_editFullName:
                dialogEditFullName();
                break;
            case R.id.layout_gitHub:
                dialogGoToWebView("https://github.com/zukhen");
                break;
            case R.id.layout_privacyPolicy:
                dialogGoToWebView("https://policies.google.com/privacy?hl=vi");

                break;
            case R.id.layout_regulation:
                dialogGoToWebView("https://drive.google.com/file/d/1jaQv7vqUj1S0NZ2MFFk26MkxbXPBEL9R/preview");
                break;
            case R.id.layout_getHelp:
                dialogGoToWebView("https://support.google.com/accounts?hl=vi#topic=3382296");
                break;
            case R.id.layout_changePassword:
                dialogChangePassword();
                break;
        }
    }

    private void dialogGoToWebView(String url) {
        bundle = new Bundle();
        bundle.putString("link", url);
        webviewFragment = new WebViewFragment();
        webviewFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, webviewFragment).addToBackStack(null).commit();
    }

    private void dialogEditFullName() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_name);
        EditText edt_firstName = dialog.findViewById(R.id.edt_firstName);
        TextView tv_errorEditFullName = dialog.findViewById(R.id.tv_errorEditFullName);
        EditText edt_lastName = dialog.findViewById(R.id.edt_lastName);
        AppCompatButton btn_saveProfile = dialog.findViewById(R.id.btn_saveProfile);
        ImageView img_closeDialog = dialog.findViewById(R.id.img_closeDialog);
//        set form
        final String fullName = SharedPrefControl.getPreferences(getContext());

        final String fName = fullName.substring(0, 2);
        final String lName = fullName.substring(3);
        edt_firstName.setText(fName);
        edt_lastName.setText(lName);



        img_closeDialog.setOnClickListener(v1 -> {
            dialog.dismiss();
        });
        btn_saveProfile.setOnClickListener(view -> {
            if (edt_firstName.getText().toString().isEmpty() || edt_lastName.getText().toString().isEmpty()) {
                tv_errorEditFullName.setText(R.string.empty);
                new Handler().postDelayed(() -> tv_errorEditFullName.setText(""), 3000);
            } else {
                final String nFullName = edt_firstName.getText().toString() + " " + edt_lastName.getText().toString();
                SharedPrefControl.savingFullName(getContext(), nFullName);
                tv_fullNameProfile.setText(SharedPrefControl.getPreferences(getContext()));
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void dialogChangePassword() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);
        EditText edt_oldPassword = dialog.findViewById(R.id.edt_oldPassword);
        EditText edt_newPassword = dialog.findViewById(R.id.edt_newPassword);
        TextView tv_errorEditFullName = dialog.findViewById(R.id.tv_errorEditFullName);
        EditText edt_rePassword = dialog.findViewById(R.id.edt_rePassword);
        AppCompatButton btn_savePassword = dialog.findViewById(R.id.btn_savePassword);
        ImageView img_closeDialog = dialog.findViewById(R.id.img_closeDialog);

        img_closeDialog.setOnClickListener(v1 -> {
            dialog.dismiss();
        });
        btn_savePassword.setOnClickListener(view -> {
            if (edt_oldPassword.getText().toString().isEmpty()||edt_rePassword.getText().toString().isEmpty() || edt_newPassword.getText().toString().isEmpty()) {
                tv_errorEditFullName.setText(R.string.empty);
                new Handler().postDelayed(() -> tv_errorEditFullName.setText(""), 3000);
            } else {
                if (!edt_newPassword.getText().toString().equals(edt_rePassword.getText().toString())) {
                    tv_errorEditFullName.setText("Mật khẩu nhập lại không đúng");
                    new Handler().postDelayed(() -> tv_errorEditFullName.setText(""), 3000);
                }
                else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Password",edt_newPassword.getText().toString());
                    editor.apply();
                    Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


}