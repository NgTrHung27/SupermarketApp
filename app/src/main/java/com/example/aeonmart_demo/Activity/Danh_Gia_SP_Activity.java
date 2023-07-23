package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.aeonmart_demo.R;

public class Danh_Gia_SP_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia_sp);
        TextView textView = findViewById(R.id.danhgia_checkbox_1);
    }
    public void onCheckBoxButtonClick(View view) {
        // Xử lý sự kiện nhấp vào TextView ở đây
        // Ví dụ: thay đổi trạng thái của TextView khi nhấp vào
        TextView textView = (TextView) view;
        textView.setText("(Chữ trong checkbox)"); // Đổi nội dung TextView khi được nhấp vào
    }
}
