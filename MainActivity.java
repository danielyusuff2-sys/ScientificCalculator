package com.sen204.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvDisplay;
    double num1 = 0;
    String operator = "";
    boolean newInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        tvDisplay.setText("0");

        int[] allIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9, R.id.btnDot,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply,
                R.id.btnDivide, R.id.btnPower,
                R.id.btnOpenBracket, R.id.btnCloseBracket,
                R.id.btnClear, R.id.btnEquals, R.id.btnNegate,
                R.id.btnPercent, R.id.btnSqrt, R.id.btnSquare,
                R.id.btnSin, R.id.btnCos, R.id.btnTan,
                R.id.btnLn, R.id.btnLog, R.id.btnPi,
                R.id.btnInverse, R.id.btnAbs
        };
        for (int id : allIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String current = tvDisplay.getText().toString();

        // --- NUMBER BUTTONS ---
        if (id == R.id.btn0 || id == R.id.btn1 || id == R.id.btn2 ||
                id == R.id.btn3 || id == R.id.btn4 || id == R.id.btn5 ||
                id == R.id.btn6 || id == R.id.btn7 || id == R.id.btn8 ||
                id == R.id.btn9) {

            String digit = ((Button) v).getText().toString();
            if (newInput || current.equals("0")) {
                tvDisplay.setText(digit);
            } else {
                tvDisplay.setText(current + digit);
            }
            newInput = false;
        }

        // --- DOT ---
        else if (id == R.id.btnDot) {
            if (newInput) {
                tvDisplay.setText("0.");
                newInput = false;
            } else if (!current.contains(".")) {
                tvDisplay.setText(current + ".");
            }
        }

        // --- CLEAR ---
        else if (id == R.id.btnClear) {
            tvDisplay.setText("0");
            num1 = 0;
            operator = "";
            newInput = true;
        }

        // --- PI ---
        else if (id == R.id.btnPi) {
            tvDisplay.setText(format(Math.PI));
            newInput = true;
        }

        // --- NEGATE ---
        else if (id == R.id.btnNegate) {
            try {
                double val = Double.parseDouble(current);
                tvDisplay.setText(format(-val));
            } catch (Exception e) {
                tvDisplay.setText("Error");
            }
        }

        // --- OPERATOR BUTTONS: +, -, *, /, ^ ---
        else if (id == R.id.btnAdd || id == R.id.btnSubtract ||
                id == R.id.btnMultiply || id == R.id.btnDivide ||
                id == R.id.btnPower) {

            try {
                num1 = Double.parseDouble(current);
                if      (id == R.id.btnAdd)      operator = "+";
                else if (id == R.id.btnSubtract) operator = "-";
                else if (id == R.id.btnMultiply) operator = "*";
                else if (id == R.id.btnDivide)   operator = "/";
                else if (id == R.id.btnPower)    operator = "^";
                newInput = true;
            } catch (Exception e) {
                tvDisplay.setText("Error");
            }
        }

        // --- EQUALS ---
        else if (id == R.id.btnEquals) {
            try {
                double num2 = Double.parseDouble(current);
                double result = 0;

                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/":
                        if (num2 == 0) { tvDisplay.setText("Error"); return; }
                        result = num1 / num2; break;
                    case "^": result = Math.pow(num1, num2); break;
                    default:  result = num2; break;
                }

                tvDisplay.setText(format(result));
                num1 = result;
                operator = "";
                newInput = true;
            } catch (Exception e) {
                tvDisplay.setText("Error");
            }
        }

        // --- SCIENTIFIC FUNCTIONS ---
        else if (id == R.id.btnSin || id == R.id.btnCos || id == R.id.btnTan ||
                id == R.id.btnLn  || id == R.id.btnLog || id == R.id.btnSqrt ||
                id == R.id.btnSquare || id == R.id.btnPercent ||
                id == R.id.btnInverse || id == R.id.btnAbs) {
            try {
                double val = Double.parseDouble(current);
                double result = 0;

                if      (id == R.id.btnSin)     result = Math.sin(Math.toRadians(val));
                else if (id == R.id.btnCos)     result = Math.cos(Math.toRadians(val));
                else if (id == R.id.btnTan)     result = Math.tan(Math.toRadians(val));
                else if (id == R.id.btnLn)      result = Math.log(val);
                else if (id == R.id.btnLog)     result = Math.log10(val);
                else if (id == R.id.btnSqrt)    result = Math.sqrt(val);
                else if (id == R.id.btnSquare)  result = val * val;
                else if (id == R.id.btnPercent) result = val / 100;
                else if (id == R.id.btnInverse) result = 1.0 / val;
                else if (id == R.id.btnAbs)     result = Math.abs(val);

                tvDisplay.setText(format(result));
                newInput = true;
            } catch (Exception e) {
                tvDisplay.setText("Error");
            }
        }

        // --- BRACKETS (just append to display) ---
        else if (id == R.id.btnOpenBracket) {
            if (newInput) tvDisplay.setText("(");
            else tvDisplay.setText(current + "(");
            newInput = false;
        }
        else if (id == R.id.btnCloseBracket) {
            tvDisplay.setText(current + ")");
        }
    }

    // Format: remove .0 from whole numbers
    private String format(double val) {
        if (Double.isNaN(val) || Double.isInfinite(val)) return "Error";
        if (val == (long) val) return String.valueOf((long) val);
        return String.valueOf(val);
    }
}