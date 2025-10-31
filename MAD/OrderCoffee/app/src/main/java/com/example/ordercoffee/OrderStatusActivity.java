package com.example.ordercoffee;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderStatusActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvOrderStatus;
    private Handler mainHandler;
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        progressBar = findViewById(R.id.progressBar);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        mainHandler = new Handler(Looper.getMainLooper());

        var name = getIntent().getStringExtra("name");
        var coffee = getIntent().getStringExtra("coffee");
        var addition = getIntent().getStringExtra("addition");
        tvOrderStatus.setText(String.format("Заказ готовится для %s:\n%s с %s", name, coffee, addition));

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus++;
                mainHandler.post(() -> progressBar.setProgress(progressStatus));
                try { //noinspection BusyWait
                    Thread.sleep(100); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
            }
            mainHandler.post(() -> {
               progressBar.setVisibility(View.GONE);
               tvOrderStatus.setText("Заказ готов. Спасибо");
            });
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainHandler.removeCallbacksAndMessages(null);
    }
}
