package com.example.aeonmart_demo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aeonmart_demo.Adapter.BillAdapter;
import com.example.aeonmart_demo.Adapter.GioHangAdapter;
import com.example.aeonmart_demo.Adapter.XacNhanThanhToanAdapter;
import com.example.aeonmart_demo.Model.BillModel;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView BillRC;
    private BillAdapter adapter;
    private List<GioHangModel> gioHangList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        gioHangList = new ArrayList<>();
        BillRC = findViewById(R.id.rv_Bill);
        BillRC.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BillAdapter(this,gioHangList);
        BillRC.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadDataFromFirestore();

        TextView tvTongGia = findViewById(R.id.Bill_TongTien);
        Button XacNhan = findViewById(R.id.Bill_HoanThanh);
        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BillActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });

        // Nhận giá trị totalPrice từ Intent và gán cho XNTT_tvGia
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        tvTongGia.setText(String.format("%.0fĐ", totalPrice));

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