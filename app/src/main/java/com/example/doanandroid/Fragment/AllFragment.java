package com.example.doanandroid.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.doanandroid.Adapter.EveningAdapter;
import com.example.doanandroid.Model.Location;
import com.example.doanandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFragment extends Fragment implements AdapterView.OnItemClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postRef = db.collection("posts");
    CollectionReference userRef = db.collection("Users");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object AllFragment;

    public AllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_all, container, false);
        return v;
    }
    @SuppressLint("LongLogTag")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView lvData = view.findViewById(R.id.lvData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        lvData.setLayoutManager(gridLayoutManager);
        List<String> listUsernames = new ArrayList<>();
        List<String> listTittles = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        List<List<String>> list=new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> imgUsers = new ArrayList<>();

        postRef.orderBy("time").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    id.add(documentSnapshot.getId());
                                    listUsernames.add(documentSnapshot.getString("username"));
                                    listTittles.add(documentSnapshot.getString("tittle"));
                                    listName.add(documentSnapshot.getString("name"));
                                    List<String> imgs = (List<String>) documentSnapshot.get("listImages");
                                    list.add(imgs);
                                    imgUsers.add(documentSnapshot.getString("imgUser"));

                            }


                            ArrayList<Location> locationArrayList = new ArrayList<>();
                            for(int i=listTittles.size()-1;i>=0;i--){
                                Location lct = new Location(id.get(i),list.get(i).get(0),imgUsers.get(i),listTittles.get(i),listName.get(i));
                                locationArrayList.add(lct);
                            }


                            EveningAdapter adapter;
                            adapter = new EveningAdapter(AllFragment.this, R.layout.item_location, locationArrayList);
                            lvData.setAdapter(adapter);


                        }
                    }
                });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}