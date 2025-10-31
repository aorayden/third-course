package com.example.ordercoffee;

import android.content.Intent;
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
        var addition = getSelectedAddition();

        var intent = new Intent(this, OrderStatusActivity.class);

        if (coffee.equals("Нет")) {
            // Блюдо - да, кофе с добавками - не отображаем.
            if (dish.equals("Нет")) {
                Toast.makeText(this, "Выберите хотя-бы что-то", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Заказ для " + name + ":\n" + dish, Toast.LENGTH_LONG).show();
            intent.putExtra("name", name);
            intent.putExtra("dish", dish);
        } else if (dish.equals("Нет")) {
            // Кофе с добавками - да, блюдо - не отображаем.
            if (addition.equals("Без добавок")) {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + " без добавок", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + "с " + addition, Toast.LENGTH_LONG).show();
            }

            intent.putExtra("name", name);
            intent.putExtra("coffee", coffee);
            intent.putExtra("addition", addition);
        } else {
            // Кофе с добавками и блюдо - да.
            if (addition.equals("Без добавок")) {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + " без добавок" + "\n" + dish, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Заказ для " + name + ":\n" + coffee + "с " + addition + "\n" + dish, Toast.LENGTH_LONG).show();
            }

            intent.putExtra("name", name);
            intent.putExtra("coffee", coffee);
            intent.putExtra("addition", addition);
            intent.putExtra("dish", dish);
        }
        startActivity(intent);
    }

    private void processSelectCoffee() {

    }

    private String getSelectedAddition() {
        var id = radioGroupAdditions.getCheckedRadioButtonId();
        if (id != -1) {
            return ((RadioButton) findViewById(id)).getText().toString();
        }
        return "Без добавок";
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence sequence, int st, int c, int a) {

        }
        @Override public void afterTextChanged(Editable s) {

        }
    }
}