package com.example.aeonmart_demo.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeonmart_demo.Adapter.FavouriteAdapter;
import com.example.aeonmart_demo.Model.FavouriteModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    StorageReference storageRef;
    FirebaseFirestore db;
    ArrayList<FavouriteModel> favouriteModels;
    FavouriteAdapter favouriteAdapter;
    RecyclerView FavouriteWatching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mục yêu thích");
        getSupportActionBar().setLogo(R.drawable.aeonminilogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        FirebaseApp.initializeApp(this);

        favouriteModels = new ArrayList<>();
        favouriteAdapter = new FavouriteAdapter(favouriteModels);

        FavouriteWatching = findViewById(R.id.rv_FavWatching);
        FavouriteWatching.setLayoutManager(new GridLayoutManager(this,2));
        FavouriteWatching.setAdapter(favouriteAdapter);
        db = FirebaseFirestore.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        loadFavouriteData();
        ImageButton btnClearFav = findViewById(R.id.btn_Clear_Fav);
        btnClearFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Favourite").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            String documentId = documentSnapshot.getId();
                            if (documentId.equals("1")){
                                continue;
                            }
                            db.collection("Favourite").document(documentId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(FavouriteActivity.this, "Delete Success!!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        favouriteModels.clear(); // Đưa ra ngoài vòng lặp để xoá toàn bộ dữ liệu yêu thích trước khi tải lại từ Firestore
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FavouriteActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private void loadFavouriteData(){
        db.collection("Favourite").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    if (documentSnapshot.getId().equals("1")){
                        continue;
                    }

                    Object imageFavObj = documentSnapshot.get("ImageFav");
                    Object nameFavObj = documentSnapshot.get("NameFav");

                    if (imageFavObj != null && nameFavObj != null) {
                        // Trong hàm loadFavouriteData()
                        String ImageFav = imageFavObj.toString();
                        String NameFav = nameFavObj.toString();
                        String id = documentSnapshot.getId(); // Lấy id từ documentSnapshot
                        favouriteModels.add(new FavouriteModel(ImageFav, NameFav, id)); // Truyền giá trị id vào đối tượng FavouriteModel

                    } else {
                        // Xử lý trường hợp dữ liệu thiếu trường "ImageFav" hoặc "NameFav"
                        // (nếu cần thiết, ví dụ: hiển thị mặc định hoặc không thêm vào danh sách)
                    }
                }
                favouriteAdapter.notifyDataSetChanged();
            } else {
                // Xử lý lỗi khi không thể lấy dữ liệu từ Firebase Firestore
                Toast.makeText(FavouriteActivity.this, "Error fetching data from Firestore", Toast.LENGTH_SHORT).show();
            }
        });
    }
}