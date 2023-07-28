package com.example.aeonmart_demo.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeonmart_demo.Adapter.HomeAdapter;
import com.example.aeonmart_demo.Adapter.SlideAdapter;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.Model.SlideViewModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    StorageReference storageRef;
    FirebaseFirestore db;
    ArrayList<HomeModel> homeModels;
    HomeAdapter homeAdapter;
    ArrayList<SlideViewModel> slideViewModels;
    SlideAdapter slideViewAdapter;
    RecyclerView rv_Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setLogo(R.drawable.aeonminilogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        FirebaseApp.initializeApp(this);
        SliderView sliderView = findViewById(R.id.sliderView);
        slideViewAdapter = new SlideAdapter(this);
        sliderView.setSliderAdapter(slideViewAdapter);
        sliderView.setCurrentPagePosition(0);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycle(true);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setScrollTimeInSec(3);
        renewItems(sliderView);
        //endregion
        homeModels = new ArrayList<>();
        homeAdapter = new HomeAdapter(homeModels);
        db = FirebaseFirestore.getInstance();
        rv_Home = findViewById(R.id.rv_Home);
        rv_Home.setLayoutFrozen(true);
        rv_Home.isLayoutSuppressed();
        rv_Home.setAdapter(homeAdapter);
        rv_Home.setLayoutManager(new GridLayoutManager(this,2));
        loadSlider();
        loadProductdata();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu_toolbar,menu);
        //region Search
        MenuItem item = menu.findItem(R.id.mn_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //call when press search button
                seachData(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //call when type letter
                if (s.isEmpty()){
                    homeModels.clear();
                    loadProductdata();
                }else {
                    rv_Home.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        //endregion
        return super.onCreateOptionsMenu(menu);
    }
    void seachData(String s){
        Query query = db.collection("Product").orderBy("Name").startAt(s).endAt(s+"\uf8ff");      // \uf8ff match all unicode value start with s
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                homeModels.clear();
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    String category = documentSnapshot.get("Category").toString();
                    String description = documentSnapshot.get("Description").toString();
                    String image = documentSnapshot.get("Image").toString();
                    String masp = documentSnapshot.get("MaSp").toString();
                    String name = documentSnapshot.get("Name").toString();
                    String origin = documentSnapshot.get("Origin").toString();
                    Double price = documentSnapshot.getDouble("Price").doubleValue();
                    String rate = documentSnapshot.get("Rate").toString();
                    homeModels.add(new HomeModel(category, description, image, masp, name, origin, price, rate));
                }
                homeAdapter = new HomeAdapter(homeModels);
                rv_Home.setAdapter(homeAdapter);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        });
    }
    private void loadSlider(){
        db.collection("Banner").get().addOnCompleteListener(task -> {
            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                String Name = queryDocumentSnapshot.get("Name").toString();
                String Pic = queryDocumentSnapshot.get("Pic").toString();
                slideViewModels.add(new SlideViewModel(Name,Pic));
            }
            slideViewAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "Error!!!", Toast.LENGTH_SHORT).show());
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadProductdata() {
        db.collection("Product").get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                String category = documentSnapshot.get("Category").toString();
                String description = documentSnapshot.get("Description").toString();
                String image = documentSnapshot.get("Image").toString();
                String masp = documentSnapshot.get("MaSp").toString();
                String name = documentSnapshot.get("Name").toString();
                String origin = documentSnapshot.get("Origin").toString();
                Double price = documentSnapshot.getDouble("Price").doubleValue();
                String rate = documentSnapshot.get("Rate").toString();
                homeModels.add(new HomeModel(category, description, image, masp, name, origin, price, rate));
            }
            homeAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "ERORR!!!", Toast.LENGTH_LONG).show();
        });
    }
    private void renewItems(View view) {
        slideViewModels = new ArrayList<>();
        SlideViewModel dataItems = new SlideViewModel();
        slideViewModels.add(dataItems);
        slideViewAdapter.renewItems(slideViewModels);
        slideViewAdapter.deleteItems(0);
    }
}