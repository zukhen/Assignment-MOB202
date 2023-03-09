package com.example.myapplication.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment.Revenue.TabRevenueFragment;
import com.example.myapplication.fragment.Revenue.TabTypeOfRevenueFragment;

public class ViewPagerRevenue extends FragmentStateAdapter {
    public ViewPagerRevenue(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TabTypeOfRevenueFragment();
        }
        return new TabRevenueFragment();


    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
