package com.example.doanandroid;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.doanandroid.Adapter.SlideAdapter;
import com.example.doanandroid.Adapter.TabAdapter;
import com.example.doanandroid.Fragment.AllFragment;
import com.example.doanandroid.Intro_Login.Login;
import com.example.doanandroid.Model.SlideImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    BottomNavigationView bottomHome;
    ViewPager2 viewPager2;
    Handler handler =new Handler();
    TabLayout tabLayout;
    ViewPager2 vpg2;
    TabAdapter tabAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText search_nav;
    //khai báo biến lưu cục bộ
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }
    ///
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        viewPager2=findViewById(R.id.viewpager2_slide);
        search_nav=findViewById(R.id.search_nav);

        SlideAdapter adapter=new SlideAdapter(getList());
        viewPager2.setAdapter(adapter);
        AutoRunSlider();
        SetTabLayout();
        initPreferences();

     /*   Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;*/

        //lưu cục bộ - để  làm cmt hiện username
        String getUsername = sharedPreferences.getString("username","");
        //
        String username;
        if(getUsername==null||getUsername.equals("")){
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }
        else username=getUsername;
     /*   String username=bundle.get("username").toString();*/


        setBottomNav(getUsername);

        search_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("locationID","location1");
//                i.putExtras(bundle);
                startActivity(i);
            }
        });


    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem()==getList().size()-1){
                viewPager2.setCurrentItem(0);
            }
            else  viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };
    //time chạy slider
    private void AutoRunSlider(){
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2500);
            }
        });
    }
    private List<SlideImage> getList(){
        List<SlideImage> list=new ArrayList<>();
        list.add(new SlideImage(R.drawable.sl1));
        list.add(new SlideImage(R.drawable.sl2));
        list.add(new SlideImage(R.drawable.sl3));
        return list;
    }
    private void setBottomNav(String username ){
        bottomHome=findViewById(R.id.bottom_home);
        bottomHome.setSelectedItemId(R.id.action_home);

        bottomHome.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_add:
                    Intent intent=new Intent(getApplicationContext(),AddActivity.class);
                    Bundle v=new Bundle();
                    v.putString("username",username);
                    intent.putExtras(v);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_home:

                    return true;
                case R.id.action_profile:
                    Intent profileIntent=new Intent(getApplicationContext(),ProfileActivity.class);
                    Bundle dataProfile=new Bundle();
                    dataProfile.putString("username",username);
                    profileIntent.putExtras(dataProfile);
                    startActivity(profileIntent);
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }


    private void SetTabLayout(){
        tabLayout=findViewById(R.id.tab_layout);
        vpg2=findViewById(R.id.vpg2_tab);
        FragmentManager fm= getSupportFragmentManager();
        tabAdapter=new TabAdapter(fm,getLifecycle());
        vpg2.setAdapter(tabAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Tất cả"));
        tabLayout.addTab(tabLayout.newTab().setText("Ăn sáng"));
        tabLayout.addTab(tabLayout.newTab().setText("Ăn chiều"));
        tabLayout.addTab(tabLayout.newTab().setText("Ăn tối"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpg2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vpg2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }
}