package com.example.doanandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doanandroid.Adapter.SearchAdapter;
import com.example.doanandroid.Model.Location;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recData;
    ImageView backMain;
    Button btnSearch;
    EditText search_nav;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postRef = db.collection("posts");
    CollectionReference userRef = db.collection("Users");
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recData= findViewById(R.id.recData);
        btnSearch= findViewById(R.id.btnSearch);
        search_nav= findViewById(R.id.search_nav);
        backMain= findViewById(R.id.imgBackMain);
        backMain.setOnClickListener(v -> onBackPressed());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recData.setLayoutManager(gridLayoutManager);
        List<String> listUsernames = new ArrayList<>();
        List<String> listTittles = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        List<List<String>> list=new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> imgUsers = new ArrayList<>();

        postRef .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                            id.add(documentSnapshot.getId());
                            listUsernames.add(documentSnapshot.getString("username"));
                            listTittles.add(documentSnapshot.getString("tittle"));
                            listName.add(documentSnapshot.getString("name"));
                            List<String> imgs = (List<String>) documentSnapshot.get("listImages");
                            imgUsers.add(documentSnapshot.getString("imgUser"));
                            list.add(imgs);

                        }


                        ArrayList<Location> locationArrayList = new ArrayList<>();
                        for (int i = 0; i < listTittles.size(); i++) {
                            Location lct = new Location(id.get(i),list.get(i).get(0),imgUsers.get(i),listTittles.get(i) ,listName.get(i) );
                            locationArrayList.add(lct);
                        }

                        SearchAdapter searchAdapter;
                        searchAdapter = new SearchAdapter(SearchActivity.this, R.layout.item_location, locationArrayList);
                        recData.setAdapter(searchAdapter);


                    }
                });

        //xử lý tìm kiếm

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword=search_nav.getText().toString();
                if(keyword.equals(""))
                    Toast.makeText(SearchActivity.this, "Bạn cần nhập gì đó để tìm kiếm", Toast.LENGTH_SHORT).show();
                else{
                    //chuyển thành chữ thường
                    keyword.toLowerCase();

                    List<String> listUsernamess = new ArrayList<>();
                    List<String> listTittless = new ArrayList<>();
                    List<String> listNames = new ArrayList<>();
                    List<List<String>> lists=new ArrayList<>();
                    List<String> ids = new ArrayList<>();
                    List<String> imgUserss = new ArrayList<>();;
                    ArrayList<Location> locationArrayList = new ArrayList<>();

                    postRef .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        keyword.toLowerCase();

                                        String tittle= documentSnapshot.getString("tittle").toLowerCase();
                                        String[] words=keyword.split("\\s");
                                        //lẩu , bò
                                        String flag="c";

                                        for(String w:words){
                                            if(tittle.contains(w.toLowerCase()) &&flag=="c"){
                                                ids.add(documentSnapshot.getId());
                                                listUsernamess.add(documentSnapshot.getString("username"));
                                                listTittless.add(documentSnapshot.getString("tittle"));
                                                listNames.add(documentSnapshot.getString("name"));
                                                List<String> imgss = (List<String>) documentSnapshot.get("listImages");
                                                imgUserss.add(documentSnapshot.getString("imgUser"));
                                                lists.add(imgss);

                                                flag=tittle;
                                            }
                                        }


                                    }



                                    for (int i = 0; i < listTittless.size(); i++) {
                                        Location lct = new Location(ids.get(i),lists.get(i).get(0),imgUserss.get(i),listTittless.get(i) ,listNames.get(i) );
                                        locationArrayList.add(lct);
                                    }

                                    SearchAdapter searchAdapter;
                                    searchAdapter = new SearchAdapter(SearchActivity.this, R.layout.item_location, locationArrayList);
                                    recData.setAdapter(searchAdapter);



                                }
                            });





                }

            }
        });
    }
}