package com.example.myapplication.fragment.LoginAndRegister;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Untils.SharedPrefControl;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private LinearLayout layout_Register, layout_havePassword;
    private EditText ed_ResUsername, ed_ResPassword, ed_ResREPassword, ed_resFullName;
    private CheckBox chk_Conditions;
    private TextView tv_Conditions, tvError, tvErrorPwd, tvErrorREPwd, tv_errorFullName;
    private Button btn_Register;
    private ImageView img_Visible1, img_Visible, img_done, img_done1;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {

        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);


        registerForOnClick();
        onTextChange(ed_ResUsername);
        onTextChange(ed_ResPassword);
        onTextChange(ed_ResREPassword);
//        animation
        layout_Register.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade));
    }

    private void registerForOnClick() {
        img_Visible.setOnClickListener(this);
        img_Visible1.setOnClickListener(this);
        btn_Register.setOnClickListener(this);
        layout_havePassword.setOnClickListener(this);
        tv_Conditions.setOnClickListener(this);
    }

    private void initUi(View view) {
        ed_ResPassword = view.findViewById(R.id.ed_ResPassword);
        ed_ResUsername = view.findViewById(R.id.ed_ResUsername);
        ed_ResREPassword = view.findViewById(R.id.ed_ResREPassword);
        layout_Register = view.findViewById(R.id.layout_Register);
        chk_Conditions = view.findViewById(R.id.chk_Conditions);
        tv_Conditions = view.findViewById(R.id.tv_Conditions);
        btn_Register = view.findViewById(R.id.btn_Register);
        img_Visible1 = view.findViewById(R.id.img_Visible1);
        img_Visible = view.findViewById(R.id.img_Visible);
        tvError = view.findViewById(R.id.tvError);
        tvErrorPwd = view.findViewById(R.id.tvErrorPwd);
        tvErrorREPwd = view.findViewById(R.id.tvErrorREPwd);
        ed_resFullName = view.findViewById(R.id.ed_resFullName);
        img_done = view.findViewById(R.id.img_done);
        img_done1 = view.findViewById(R.id.img_done1);
        tv_errorFullName = view.findViewById(R.id.tv_errorFullName);
        layout_havePassword = view.findViewById(R.id.layout_havePassword);
    }

    private void onTextChange(EditText editText) {
        switch (editText.getId()) {
            case R.id.ed_ResUsername:
                tvError.setText(null);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().isEmpty() || charSequence.length() == 0) {
                            tvError.setText(R.string.empty);
                            img_done.setImageResource(R.drawable.ic_baseline_close_24);

                        } else {
                            if (charSequence.length() <= 3) {
                                tvError.setText("Tài khoản phải nhiều hơn 3 kí tự");
                                img_done.setImageResource(R.drawable.ic_baseline_close_24);
                            } else {
                                tvError.setText(null);
                                img_done.setImageResource(R.drawable.ic_baseline_done_24);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                break;
            case R.id.ed_resFullName:
                tv_errorFullName.setText(null);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().isEmpty() || charSequence.length() == 0) {
                            tv_errorFullName.setText(R.string.empty);
                            img_done.setImageResource(R.drawable.ic_baseline_close_24);

                        } else {
                            tvError.setText(null);
                            img_done.setImageResource(R.drawable.ic_baseline_done_24);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                break;
            case R.id.ed_ResPassword:
                tvError.setText(null);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().isEmpty() && charSequence.length() == 0) {
                            tvErrorPwd.setText("Vui lòng không để trống");
                        } else {
                            if (charSequence.length() <= 3) {
                                tvErrorPwd.setText("Mật khẩu phải nhiều hơn 3 kí tự");
                            } else {
                                tvErrorPwd.setText(null);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;
            case R.id.ed_ResREPassword:
                tvError.setText(null);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().isEmpty() && charSequence.length() == 0) {
                            tvErrorREPwd.setText("Vui lòng không để trống");
                        } else {
                            if (charSequence.length() <= 3) {
                                tvErrorREPwd.setText("Mật khẩu phải nhiều hơn 3 kí tự");
                            } else {
                                if (!charSequence.toString().equals(ed_ResPassword.getText().toString())) {
                                    tvErrorREPwd.setText("Mật khẩu nhập lại không đúng");
                                } else {
                                    tvErrorREPwd.setText(null);
                                }
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Conditions:
                Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_conditions);
                dialog.show();
                break;
            case R.id.img_Visible:
                if (ed_ResPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    ed_ResPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    img_Visible.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                } else {
                    ed_ResPassword.setInputType(LoginFragment.REQUEST_CODE);
                    img_Visible.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }
                break;
            case R.id.img_Visible1:
                if (ed_ResREPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    ed_ResREPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    img_Visible1.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                } else {
                    ed_ResREPassword.setInputType(LoginFragment.REQUEST_CODE);
                    img_Visible1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }
                break;
            case R.id.btn_Register:
                if (ed_ResREPassword.getText().toString().isEmpty() || ed_ResPassword.getText().toString().isEmpty() || ed_ResUsername.getText().toString().isEmpty() || ed_resFullName.getText().toString().isEmpty()) {
                    tvErrorREPwd.setText(R.string.empty);
                    tv_errorFullName.setText(R.string.empty);
                    tvError.setText(R.string.empty);
                    tvErrorPwd.setText(R.string.empty);
                } else {
                    if (!chk_Conditions.isChecked()) {
                        Toast.makeText(getActivity(), "Vui lòng đọc Điều khoản và điều kiện", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("RegUsername", ed_ResUsername.getText().toString());
                        editor.putString("RegPassword", ed_ResPassword.getText().toString());
                        editor.apply();
                        Snackbar snackbar = Snackbar.make(getView(), "Đăng ký thành công", 2000);
                        snackbar.show();
                        SharedPrefControl.savingFullName(getContext(),ed_resFullName.getText().toString());
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
                break;
            case R.id.layout_havePassword:
                getActivity().getSupportFragmentManager().popBackStack();
                break;

        }
    }


}