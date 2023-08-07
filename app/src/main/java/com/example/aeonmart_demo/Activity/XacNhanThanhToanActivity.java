package com.example.aeonmart_demo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import com.example.aeonmart_demo.Adapter.GioHangAdapter;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.Model.User;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XacNhanThanhToanActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private GioHangAdapter adapter;
    private List<GioHangModel> gioHangList;
    private List<User> usersmodel;
    private boolean isMoMoSelected = false;
    private boolean isTienMatSelected = false;
    private boolean isTechcombankSelected = false;

    private ImageSwitcher imgMoMo;
    private ImageSwitcher imgTienMat;
    private ImageSwitcher imgTechcombank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_thanh_toan);

        gioHangList = new ArrayList<>();
        recyclerView = findViewById(R.id.XNTT_Rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GioHangAdapter(this, gioHangList);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        loadDataFromFirestore();

        TextView tvTongGia = findViewById(R.id.XNTT_tvGia);
        Button XacNhan = findViewById(R.id.XNTT_btn_XacNhan);
        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(XacNhanThanhToanActivity.this, BillActivity.class);
//                i.putExtra("gioHangList", (Serializable) gioHangList);
                startActivity(i);
            }
        });

        // Nhận giá trị totalPrice từ Intent và gán cho XNTT_tvGia
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        tvTongGia.setText(String.format("%.0fĐ", totalPrice));

        // Khởi tạo ImageSwitcher và bắt sự kiện khi người dùng chọn
        imgMoMo = findViewById(R.id.XNTT_imgMoMo);
        imgMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMoMo();
            }
        });

        imgTienMat = findViewById(R.id.XNTT_imgtienmat);
        imgTienMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTienMat();
            }
        });

        imgTechcombank = findViewById(R.id.XNTT_imgTechcombank);
        imgTechcombank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTechcombank();
            }
        });
    }

    private void selectMoMo() {
        isMoMoSelected = true;
        isTienMatSelected = false;
        isTechcombankSelected = false;
        updateUI();
    }

    private void selectTienMat() {
        isMoMoSelected = false;
        isTienMatSelected = true;
        isTechcombankSelected = false;
        updateUI();
    }

    private void selectTechcombank() {
        isMoMoSelected = false;
        isTienMatSelected = false;
        isTechcombankSelected = true;
        updateUI();
    }

    private void updateUI() {
        imgMoMo.setBackgroundResource(isMoMoSelected ? R.drawable.logomomo_selected : R.drawable.logomomo);
        imgTienMat.setBackgroundResource(isTienMatSelected ? R.drawable.thanhtoantienmat_selected : R.drawable.thanhtoantienmat);
        imgTechcombank.setBackgroundResource(isTechcombankSelected ? R.drawable.logotechcombank_selected : R.drawable.logotechcombank);
    }

    private void loadDataFromFirestore() {
        CollectionReference gioHangRef = db.collection("cart");

        gioHangRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    gioHangList.clear(); // Clear the list to avoid duplicates
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert each document to GioHangModel and add it to the list
                        GioHangModel gioHangModel = document.toObject(GioHangModel.class);
                        gioHangList.add(gioHangModel);
                    }
                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle errors
                }
            }
        });
    }
}
