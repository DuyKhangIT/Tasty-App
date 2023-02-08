package com.example.doanandroid.Intro_Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.doanandroid.Adapter.OnboardingAdapter;
import com.example.doanandroid.MainActivity;
import com.example.doanandroid.Model.OnboardingItem;
import com.example.doanandroid.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    private LinearLayout layoutOnboardingInicator;
    private OnboardingAdapter onboardingAdapter;
    private MaterialButton btnOnboardingAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        btnOnboardingAction = findViewById(R.id.btnOnBoardingAction);
        layoutOnboardingInicator = findViewById(R.id.LayoutOnBoardingIndicators);
        setuponboardingItems();
        ViewPager2 onboardingViewPager2 = findViewById(R.id.ViewPagerOnBoarding);
        onboardingViewPager2.setAdapter(onboardingAdapter);
        btnOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager2.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager2.setCurrentItem(onboardingViewPager2.getCurrentItem() +1 );
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });

        setupOnboardingIndicator();

        setCurrentOnboardingIndicator(0);

        //hieu ung dau 3 cham xanh dam hoac xanh nhat khi chuyen man hinh animation
        onboardingViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });
    }
    //set up de chay tung animation
    private void setuponboardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemAnimation1 = new OnboardingItem();
        itemAnimation1.setTvTextTitle("Đa dạng món ăn");
        itemAnimation1.setAnimation1(R.raw.animation1);

        OnboardingItem itemAnimation2 = new OnboardingItem();
        itemAnimation2.setTvTextTitle("Xem đánh giá chi tiết");
        itemAnimation2.setAnimation1(R.raw.animation2);

        OnboardingItem itemAnimation3 = new OnboardingItem();
        itemAnimation3.setTvTextTitle("Tìm kiếm thuận tiện, nhanh chóng");
        itemAnimation3.setAnimation1(R.raw.animation3);

        onboardingItems.add(itemAnimation1);
        onboardingItems.add(itemAnimation2);
        onboardingItems.add(itemAnimation3);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    //set up dau 3 cham o man hinh chuyen animation
    private void setupOnboardingIndicator()
    {
        ImageView[] indicator = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i =0; i< indicator.length;i++)
        {
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            indicator[i].setLayoutParams(layoutParams);
            layoutOnboardingInicator.addView(indicator[i]);
        }
    }
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingInicator.getChildCount();
        for(int i=0; i<childCount;i++)
        {
            ImageView imageView = (ImageView) layoutOnboardingInicator.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                );
            }
            else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            }

        }
        //set nút start - next trong animation
        if(index == onboardingAdapter.getItemCount()-1){
            btnOnboardingAction.setText("Bắt đầu");
        }
        else {
            btnOnboardingAction.setText("Tiếp theo");
        }
    }
}