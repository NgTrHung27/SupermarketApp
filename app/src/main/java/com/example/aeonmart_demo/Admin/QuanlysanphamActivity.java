package com.example.aeonmart_demo.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aeonmart_demo.Adapter.HomeAdapter;
import com.example.aeonmart_demo.Adapter.SlideAdapter;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.Model.SlideViewModel;
import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class QuanlysanphamActivity extends AppCompatActivity {
    StorageReference storageRef;
    FirebaseFirestore db;
    ArrayList<HomeModel> homeModels;
    HomeAdapter homeAdapter;
    ArrayList<SlideViewModel> slideViewModels;
    SlideAdapter slideViewAdapter;
    RecyclerView rv_Home;
    Button btnthemsanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlysanpham);
        homeModels = new ArrayList<>();
        homeAdapter = new HomeAdapter(homeModels,this);
        db = FirebaseFirestore.getInstance();
        rv_Home = findViewById(R.id.rv_quanlysanpham);
//        rv_Home.setLayoutFrozen(true);
//        rv_Home.isLayoutSuppressed();
        rv_Home.setAdapter(homeAdapter);
        rv_Home.setLayoutManager(new GridLayoutManager(this,2));
        loadProductdata();

        btnthemsanpham=findViewById(R.id.quanlysanpham_btn_them);

        btnthemsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(QuanlysanphamActivity.this, ThemSanPhamActivity.class);
                startActivity(i);
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadProductdata() {
        db.collection("Product").get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                String category = documentSnapshot.get("Category").toString();
                String description = documentSnapshot.get("Description").toString();
                Boolean favstatus = Boolean.valueOf(documentSnapshot.get("FavStatus").toString());
                String image = documentSnapshot.get("Image").toString();
                String masp = documentSnapshot.get("MaSp").toString();
                String name = documentSnapshot.get("Name").toString();
                String origin = documentSnapshot.get("Origin").toString();
                Double price = documentSnapshot.getDouble("Price").doubleValue();
                String rate = documentSnapshot.get("Rate").toString();
                homeModels.add(new HomeModel(category, description,favstatus,image, masp, name, origin, price, rate));
            }
            homeAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "ERORR!!!", Toast.LENGTH_LONG).show();
        });
    }
}