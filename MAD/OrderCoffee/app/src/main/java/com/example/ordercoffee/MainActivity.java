package com.example.ordercoffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editName;
    private TextView selectAdditions;
    private Spinner spinnerCoffee;
    private Spinner spinnerDish;
    private RadioGroup radioGroupAdditions;
    private Button buttonOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addImageToCoffeeSpinner();
        addImageToDishSpinner();
        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        editName = findViewById(R.id.editName);
        selectAdditions = findViewById(R.id.selectAdditions);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        radioGroupAdditions = findViewById(R.id.radioGroupAdditions);
        buttonOrder = findViewById(R.id.buttonOrder);
        spinnerDish = findViewById(R.id.spinnerDish);
    }

    private void setupListeners() {
        editName.addTextChangedListener(new SimpleTextWatcher() {
            @Override public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(sequence)) {
                    editName.setBackgroundResource(R.drawable.edit_text_background);
                }
            }
        });
        buttonOrder.setOnClickListener(v -> processOrder());
        spinnerCoffee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerCoffee.getSelectedItem().toString().equals("Нет")) {
                    int count = radioGroupAdditions.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View child = radioGroupAdditions.getChildAt(i);
                        if (child instanceof RadioButton) {
                            RadioButton radioButton = (RadioButton) child;
                            radioButton.setEnabled(false);
                            radioButton.setChecked(false);
                        }
                    }
                } else {
                    int count = radioGroupAdditions.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View child = radioGroupAdditions.getChildAt(i);
                        if (child instanceof RadioButton) {
                            RadioButton radioButton = (RadioButton) child;
                            radioButton.setEnabled(true);
                            if (i == 0) {
                                radioButton.setChecked(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void processOrder() {
        var name = editName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            editName.setBackgroundResource(R.drawable.edit_text_error_background);
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            return;
        }
        var coffee = spinnerCoffee.getSelectedItem().toString();
        var dish = spinnerDish.getSelectedItem().toString();
        var addition = getSelectedAddition().toLowerCase();

        var intent = new Intent(this, OrderStatusActivity.class);

        // Получаем URI изображений из адаптеров (если доступны)
        if (spinnerCoffee.getAdapter() instanceof CoffeeAdapter) {
            CoffeeAdapter coffeeAdapter = (CoffeeAdapter) spinnerCoffee.getAdapter();
            java.net.URI ignore = null; // no-op to keep formatting consistent
            Uri coffeeUri = coffeeAdapter.getImageUri(spinnerCoffee.getSelectedItemPosition());
            if (coffeeUri != null) {
                intent.putExtra("coffee_image_uri", coffeeUri.toString());
            }
        }
        if (spinnerDish.getAdapter() instanceof DishAdapter) {
            DishAdapter dishAdapter = (DishAdapter) spinnerDish.getAdapter();
            Uri dishUri = dishAdapter.getImageUri(spinnerDish.getSelectedItemPosition());
            if (dishUri != null) {
                intent.putExtra("dish_image_uri", dishUri.toString());
            }
        }

        if (coffee.equals("Нет")) {
            if (dish.equals("Нет")) {
                Toast.makeText(this, "Выберите хотя-бы что-то", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Заказ для " + name + ":\n" + dish, Toast.LENGTH_LONG).show();
            intent.putExtra("name", name);
            intent.putExtra("dish", dish);
        } else if (dish.equals("Нет")) {
            if (addition.equals("Без добавок")) {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + " без добавок", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + " с " + addition, Toast.LENGTH_LONG).show();
            }

            intent.putExtra("name", name);
            intent.putExtra("coffee", coffee);
            intent.putExtra("addition", addition);
        } else {
            if (addition.equals("Без добавок")) {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + " без добавок" + "\n" + dish, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + " с " + addition + "\n" + dish, Toast.LENGTH_LONG).show();
            }

            intent.putExtra("name", name);
            intent.putExtra("coffee", coffee);
            intent.putExtra("addition", addition);
            intent.putExtra("dish", dish);
        }
        startActivity(intent);
    }

    private String getSelectedAddition() {
        var id = radioGroupAdditions.getCheckedRadioButtonId();
        if (id != -1) {
            return ((RadioButton) findViewById(id)).getText().toString();
        }
        return "Без добавок";
    }

    private void addImageToCoffeeSpinner() {
        Spinner spinner = findViewById(R.id.spinnerCoffee);

        var names = getResources().getStringArray(R.array.coffee_options);
        var images = getResources().obtainTypedArray(R.array.coffee_images);

        var adapter = new CoffeeAdapter(this, names, images);
        spinner.setAdapter(adapter);
    }

    private void addImageToDishSpinner() {
        Spinner spinner = findViewById(R.id.spinnerDish);

        var names = getResources().getStringArray(R.array.dish_options);
        var images = getResources().obtainTypedArray(R.array.dish_images);

        var adapter = new DishAdapter(this, names, images);
        spinner.setAdapter(adapter);
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence sequence, int st, int c, int a) {

        }
        @Override public void afterTextChanged(Editable s) {

        }
    }
}