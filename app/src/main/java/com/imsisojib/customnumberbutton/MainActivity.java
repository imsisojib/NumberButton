package com.imsisojib.customnumberbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.imsisojib.number_button.NumberButton;

public class MainActivity extends AppCompatActivity {
    private NumberButton numberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberButton = findViewById(R.id.number_button);
        numberButton.setNumberTextSize(16);

        findViewById(R.id.button_save).setOnClickListener(v -> {
            float number = numberButton.getNumber();
            Toast.makeText(this, "Number = "+number, Toast.LENGTH_SHORT).show();
        });

    }
}