package com.example.doanandroid.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doanandroid.Fragment.AllFragment;
import com.example.doanandroid.Fragment.FirstFragment;
import com.example.doanandroid.Fragment.SecondFragment;
import com.example.doanandroid.Fragment.ThirFragment;

public class TabAdapter extends FragmentStateAdapter {
    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new FirstFragment();
            case 2:
                return new SecondFragment();
            case 3:
                return new ThirFragment();

        }
        return new AllFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
