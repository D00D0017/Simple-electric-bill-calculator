package com.example.simpleelectricbillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText kilowattEditText;
    EditText percentageEditText;
    Button calculateButton;
    TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kilowattEditText = findViewById(R.id.kilowattEditText);
        percentageEditText = findViewById(R.id.percentageEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBill();
            }

        });
    }


    private void calculateBill() {

        String kilowattString = kilowattEditText.getText().toString();
        String percentageString = percentageEditText.getText().toString();

        if (kilowattString.isEmpty()) {
            kilowattEditText.setError("Enter the number of kilowatt");
            return;
        }

        if (percentageString.isEmpty()) {
            percentageEditText.setError("Enter the rebate percentage");
            return;
        }

        double kilowatt = Double.parseDouble(kilowattEditText.getText().toString());
        float percentage = Float.parseFloat(percentageEditText.getText().toString());



        double billAmount;
        if (kilowatt <= 200) {
            billAmount = kilowatt * 21.8;
        } else if (kilowatt <= 300) {
            billAmount = 200 * 21.8 + (kilowatt - 200) * 33.4;
        } else if (kilowatt <= 600) {
            billAmount = 200 * 21.8 + 100 * 33.4 + (kilowatt - 300) * 51.6;
        } else {
            billAmount = 200 * 21.8 + 100 * 33.4 + 300 * 51.6 + (kilowatt - 600) * 54.6;
        }

        double rebateAmount = billAmount * (percentage / 100);
        double finalBillAmount = billAmount - rebateAmount;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedAmount = decimalFormat.format(finalBillAmount);
        resultTextView.setText(getString(R.string.result_text, formattedAmount));    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            // Handle reset menu item click
            Toast.makeText(this, "Opening About page...", Toast.LENGTH_SHORT).show();
            openAboutPage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAboutPage() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

}

