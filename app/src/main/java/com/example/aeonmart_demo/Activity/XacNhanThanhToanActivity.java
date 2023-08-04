package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.aeonmart_demo.Adapter.XacNhanThanhToanAdapter;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.Model.User;
import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class XacNhanThanhToanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_thanh_toan);
        List<GioHangModel> gioHangList = (List<GioHangModel>) getIntent().getSerializableExtra("gioHangList");

        RecyclerView recyclerView = findViewById(R.id.XNTT_Rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        XacNhanThanhToanAdapter adapter = new XacNhanThanhToanAdapter(gioHangList);
        recyclerView.setAdapter(adapter);

        TextView tvTongGia = findViewById(R.id.XNTT_tvGia);

        // Nhận giá trị totalPrice từ Intent và gán cho XNTT_tvGia
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        tvTongGia.setText(String.format("%.0fĐ", totalPrice));
    }



}