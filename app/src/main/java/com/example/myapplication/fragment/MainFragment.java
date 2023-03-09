package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Untils.SharedPrefControl;
import com.example.myapplication.fragment.Analysis.AnalysisFragment;
import com.example.myapplication.fragment.Expensive.ExpensesFragment;
import com.example.myapplication.fragment.LoginAndRegister.LoginFragment;
import com.example.myapplication.fragment.OtherFragment.ProfileFragment;
import com.example.myapplication.fragment.OtherFragment.WebViewFragment;
import com.example.myapplication.fragment.Revenue.RevenueFragment;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private int item_selected = R.id.nav_revenue;
    private Toolbar toolbar;
    private NavigationView navigation_view;
    private DrawerLayout drawer_layout;
    private WebViewFragment webviewFragment;


    public MainFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {

        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        setUpToolbar();
        gotoProfileFragment();
    }

    private void gotoProfileFragment() {
        View headerView = navigation_view.getHeaderView(0);
        TextView tvFullName = headerView.findViewById(R.id.tv_fullNameAdmin);
        tvFullName.setText(SharedPrefControl.getPreferences(getContext()));
        ImageView img_avatar = headerView.findViewById(R.id.image_view);
        img_avatar.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
        });
    }


    private void setUpToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        navigation_view.setNavigationItemSelectedListener(this);
        replaceFragment(new RevenueFragment());
        navigation_view.getMenu().findItem(item_selected).setChecked(true);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == item_selected) return true;
        switch (item.getItemId()) {
            case R.id.nav_revenue:
                replaceFragment(new RevenueFragment());
                break;
            case R.id.nav_expenses:
                replaceFragment(new ExpensesFragment());
                break;
            case R.id.nav_analysis:
                replaceFragment(new AnalysisFragment());
                break;
            case R.id.nav_logout:
                dialogLogout();
                break;
            case R.id.nav_introduce:
                dialogGoToWebView("file:///android_asset/index.html");
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        item_selected = item.getItemId();
        return true;
    }

    private void dialogGoToWebView(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("link", url);
        webviewFragment = new WebViewFragment();
        webviewFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, webviewFragment).addToBackStack(null).commit();
    }

    private void dialogLogout() {
        Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_logout);
        TextView tv_logout, tv_cancel;
        tv_logout = dialog.findViewById(R.id.tv_logout);
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        tv_logout.setOnClickListener(view -> {
            MainActivity._logoutAccount = true;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment).commit();
    }

    private void initUi(View view) {
        navigation_view = view.findViewById(R.id.navigation_view);
        toolbar = view.findViewById(R.id.toolbar);
        drawer_layout = view.findViewById(R.id.drawer_layout);
    }
}