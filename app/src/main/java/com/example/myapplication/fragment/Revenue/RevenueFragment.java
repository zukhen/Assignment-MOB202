package com.example.myapplication.fragment.Revenue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.ViewPager.ViewPagerRevenue;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RevenueFragment extends Fragment {
    private ViewPager2 viewPager_Revenue;
    private TabLayout tabLayout_Revenue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_revenue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        ViewPagerRevenue adapter = new ViewPagerRevenue(getActivity());
        viewPager_Revenue.setAdapter(adapter);



        //tao menu
        new TabLayoutMediator(tabLayout_Revenue, viewPager_Revenue, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Khoản Thu");
                    break;
                case 1:
                    tab.setText("Loại Thu");
                    break;
            }
        }).attach();
    }

    private void initUi(View view) {
        tabLayout_Revenue = view.findViewById(R.id.tabLayout_Revenue);
        viewPager_Revenue = view.findViewById(R.id.viewPager_Revenue);
    }
}
