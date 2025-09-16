package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private Double operand = null;
    private String pendingOp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.tvDisplay);

        // Digits
        int[] digitIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot};
        View.OnClickListener digitListener = v -> {
            Button b = (Button) v;
            String cur = display.getText().toString();
            if (cur.equals("0") && !b.getText().toString().equals(".")) {
                display.setText(b.getText());
            } else {
                display.append(b.getText());
            }
        };
        for (int id : digitIds) findViewById(id).setOnClickListener(digitListener);

        // Operators
        int[] opIds = {R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv};
        View.OnClickListener opListener = v -> {
            Button b = (Button) v;
            String op = b.getText().toString();
            String valStr = display.getText().toString();
            try {
                double value = Double.parseDouble(valStr);
                if (operand == null) {
                    operand = value;
                } else if (pendingOp != null) {
                    operand = performOperation(operand, value, pendingOp);
                    display.setText(String.valueOf(operand));
                }
                pendingOp = op;
                display.setText("");
            } catch (NumberFormatException e) {
                display.setText("");
            }
        };
        for (int id : opIds) findViewById(id).setOnClickListener(opListener);

        // Equals
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            if (pendingOp != null && operand != null) {
                try {
                    double value = Double.parseDouble(display.getText().toString());
                    double result = performOperation(operand, value, pendingOp);
                    display.setText(String.valueOf(result));
                    operand = null;
                    pendingOp = null;
                } catch (NumberFormatException e) {
                    display.setText("Error");
                }
            }
        });

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            display.setText("");
            operand = null;
            pendingOp = null;
        });
    }

    private double performOperation(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return b == 0 ? Double.NaN : a / b;
            default: return b;
        }
    }
}
