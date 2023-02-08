package com.example.doanandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectLocationActivity extends AppCompatActivity {
    ListView lvLocation;
    ArrayList<String> listLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        lvLocation=findViewById(R.id.lvLocation);
        listLocation=new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String  username =  bundle.get("username").toString();
        listLocation.add("Hồ Chí Minh");
        listLocation.add("Hà Nội");
        listLocation.add("Đà Lạt");
        listLocation.add("Vũng Tàu");
        listLocation.add("Biên Hòa");
        listLocation.add("Huế");
        listLocation.add("Bảo Lộc");
        listLocation.add("Buôn Ma Thuột");
        listLocation.add("Cam ranh");
        listLocation.add("Cần thơ");

        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listLocation);
        lvLocation.setAdapter(adapter);

        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = listLocation.get(position);
                Intent i = new Intent(SelectLocationActivity.this, MainActivity.class);
                Bundle b=new Bundle();
                b.putString("location",data);
                b.putString("username",username);
                i.putExtras(b);
                startActivity(i);

            }
        });





    }
}