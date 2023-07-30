package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aeonmart_demo.R;

import java.util.Random;

public class Quay_ThuongActivity extends AppCompatActivity {
    private ImageView imageView;
    private Animation rotateAnimation;
    private Button button;

    private boolean isSpinning = false;
    private int[] prizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}; // Giả sử có 10 giải thưởng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quay_thuong);

        imageView = findViewById(R.id.imageView2);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSpinning) {
                    startSpinningAnimation();
                }
            }
        });
    }

    private void startSpinningAnimation() {
        isSpinning = true;
        imageView.startAnimation(rotateAnimation);

        // Tạo một số ngẫu nhiên trong khoảng từ 3-6 giây để dừng quay (có thể điều chỉnh thời gian tùy ý)
        int randomDelay = new Random().nextInt(4000) + 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSpinningAnimation();
            }
        }, randomDelay);
    }

    private void stopSpinningAnimation() {
        imageView.clearAnimation();
        isSpinning = false;

        // Tính toán vị trí dừng ngẫu nhiên trên vòng quay
        int randomIndex = new Random().nextInt(prizes.length);
        int prize = prizes[randomIndex];

        // Hiển thị thông báo vị trí dừng ngẫu nhiên
        Toast.makeText(this, "Vị trí dừng: " + prize, Toast.LENGTH_SHORT).show();

        // Xử lý giải thưởng tại vị trí dừng này
        // Ví dụ: hiển thị dialog, lưu điểm số, hoặc xử lý thông tin giải thưởng khác tùy ý.

    }

}