package com.example.myapplication.fragment.Expensive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.ViewPager.ViewPagerExpenses;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesFragment extends Fragment {
    private ViewPager2 viewPager_Expenses;
    private TabLayout tabLayout_Expenses;

    public ExpensesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ExpensesFragment newInstance() {

        return new ExpensesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        ViewPagerExpenses viewPagerExpenses = new ViewPagerExpenses(getActivity());
        viewPager_Expenses.setAdapter(viewPagerExpenses);
        new TabLayoutMediator(tabLayout_Expenses, viewPager_Expenses, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.expenses);
                    break;
                case 1:
                    tab.setText(R.string.type_of_expenses);
                    break;
            }
        }).attach();
    }

    private void initUi(View view) {
        viewPager_Expenses = view.findViewById(R.id.viewPager_Expenses);
        tabLayout_Expenses = view.findViewById(R.id.tabLayout_Expenses);
    }
}