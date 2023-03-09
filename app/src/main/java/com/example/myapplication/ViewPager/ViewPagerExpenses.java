package com.example.myapplication.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment.Expensive.TabExpensesFragment;
import com.example.myapplication.fragment.Expensive.TabTypeOfExpensesFragment;

public class ViewPagerExpenses extends FragmentStateAdapter {
    public ViewPagerExpenses(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TabTypeOfExpensesFragment();
        }
        return new TabExpensesFragment();


    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
