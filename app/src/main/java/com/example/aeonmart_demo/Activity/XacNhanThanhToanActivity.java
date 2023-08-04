package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
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
        List<GioHangModel> gioHangList = (List<GioHangModel>) getIntent().getSerializableExtra("gioHangList");

        RecyclerView recyclerView = findViewById(R.id.XNTT_Rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        XacNhanThanhToanAdapter adapter = new XacNhanThanhToanAdapter(gioHangList);
        recyclerView.setAdapter(adapter);

        TextView tvTongGia = findViewById(R.id.XNTT_tvGia);
        Button XacNhan = findViewById(R.id.XNTT_btn_XacNhan);
        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(XacNhanThanhToanActivity.this, BillActivity.class);
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
}
