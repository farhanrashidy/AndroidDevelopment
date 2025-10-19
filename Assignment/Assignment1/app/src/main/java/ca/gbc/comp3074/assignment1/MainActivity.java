package ca.gbc.comp3074.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText editHours, editRate, editTax;
    TextView txtPay, txtOvertime, txtTotal, txtTax;
    Button btnCalculate, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editHours = findViewById(R.id.editHours);
        editRate = findViewById(R.id.editRate);
        editTax = findViewById(R.id.editTax);

        txtPay = findViewById(R.id.txtPay);
        txtOvertime = findViewById(R.id.txtOvertime);
        txtTotal = findViewById(R.id.txtTotal);
        txtTax = findViewById(R.id.txtTax);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnAbout = findViewById(R.id.btnAbout);

        // Calculate button
        btnCalculate.setOnClickListener(v -> calculatePay());

        // About button
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void calculatePay() {
        try {
            double hours = Double.parseDouble(editHours.getText().toString());
            double rate = Double.parseDouble(editRate.getText().toString());
            double taxRate = Double.parseDouble(editTax.getText().toString());

            double pay, overtimePay = 0;

            // Regular or overtime pay calculation
            if (hours <= 40) {
                pay = hours * rate;
            } else {
                pay = 40 * rate;
                overtimePay = (hours - 40) * rate * 1.5;
            }

            double totalPay = pay + overtimePay;
            double tax = pay * taxRate;

            // Display results
            txtPay.setText(String.format("Pay: $%.2f", pay));
            txtOvertime.setText(String.format("Overtime Pay: $%.2f", overtimePay));
            txtTotal.setText(String.format("Total Pay: $%.2f", totalPay));
            txtTax.setText(String.format("Tax: $%.2f", tax));

        } catch (Exception e) {
            Toast.makeText(this, "Please enter valid numbers!", Toast.LENGTH_SHORT).show();
        }
    }
}

