package com.example.ordercoffee;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderStatusActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvOrderStatus;
    private Handler mainHandler;
    private int progressStatus = 0;
    private ImageView ivCoffeeImage;
    private ImageView ivDishImage;
    private TextView tvForName;
    private TextView tvCoffeeDetails;
    private TextView tvDishDetails;
    private View coffeeRow;
    private View dishRow;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        progressBar = findViewById(R.id.progressBar);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        ivCoffeeImage = findViewById(R.id.ivCoffeeImage);
        ivDishImage = findViewById(R.id.ivDishImage);
        tvForName = findViewById(R.id.tvForName);
        tvCoffeeDetails = findViewById(R.id.tvCoffeeDetails);
        tvDishDetails = findViewById(R.id.tvDishDetails);
        coffeeRow = findViewById(R.id.coffeeRow);
        dishRow = findViewById(R.id.dishRow);
        btnBack = findViewById(R.id.btnBack);

        // Кнопка по клику закрывает экран
        btnBack.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        mainHandler = new Handler(Looper.getMainLooper());

        String name = getIntent().getStringExtra("name");
        String coffee = getIntent().getStringExtra("coffee");
        String dish = getIntent().getStringExtra("dish");
        String addition = getIntent().getStringExtra("addition");

        String coffeeImageUriStr = getIntent().getStringExtra("coffee_image_uri");
        String dishImageUriStr = getIntent().getStringExtra("dish_image_uri");

        if (name != null && !name.isEmpty()) {
            tvForName.setText("Для кого: " + name);
        } else {
            tvForName.setText("Для кого: ");
        }

        if (coffee != null && !"Нет".equals(coffee)) {
            coffeeRow.setVisibility(View.VISIBLE);
            String coffeeText = coffee;
            if (addition == null || "без добавок".equals(addition)) {
                coffeeText += " без добавок";
            } else {
                coffeeText += " с " + addition.toLowerCase();
            }
            tvCoffeeDetails.setText(coffeeText);

            if (coffeeImageUriStr != null && !coffeeImageUriStr.isEmpty()) {
                ivCoffeeImage.setImageURI(Uri.parse(coffeeImageUriStr));
                ivCoffeeImage.setVisibility(View.VISIBLE);
            } else {
                ivCoffeeImage.setVisibility(View.GONE);
            }
        } else {
            coffeeRow.setVisibility(View.GONE);
        }

        if (dish != null && !"Нет".equals(dish)) {
            dishRow.setVisibility(View.VISIBLE);
            tvDishDetails.setText(dish);

            if (dishImageUriStr != null && !dishImageUriStr.isEmpty()) {
                ivDishImage.setImageURI(Uri.parse(dishImageUriStr));
                ivDishImage.setVisibility(View.VISIBLE);
            } else {
                ivDishImage.setVisibility(View.GONE);
            }
        } else {
            dishRow.setVisibility(View.GONE);
        }

        tvOrderStatus.setText("Заказ готовится..");

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus++;
                mainHandler.post(() -> progressBar.setProgress(progressStatus));
                try { //noinspection BusyWait
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                tvOrderStatus.setText("Заказ готов. Спасибо");
                btnBack.setVisibility(View.VISIBLE);
            });
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
    }
}
