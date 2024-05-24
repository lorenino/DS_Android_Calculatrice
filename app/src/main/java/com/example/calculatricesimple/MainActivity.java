package com.example.calculatricesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private Operator operator = null;
    private double operand1 = 0;

    private enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        setupButtons();
    }

    private void setupButtons() {
        int[] buttonIDs = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
                R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEqual, R.id.buttonClear
        };

        for (int id : buttonIDs) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick((Button) v);
                }
            });
        }
    }

    private void onButtonClick(Button button) {
        String text = button.getText().toString();
        switch (text) {
            case "C":
                currentInput = "";
                operator = null;
                operand1 = 0;
                display.setText("0");
                break;
            case "+":
                onOperatorClick(Operator.ADD);
                break;
            case "-":
                onOperatorClick(Operator.SUBTRACT);
                break;
            case "*":
                onOperatorClick(Operator.MULTIPLY);
                break;
            case "/":
                onOperatorClick(Operator.DIVIDE);
                break;
            case "=":
                onEqualClick();
                break;
            default:
                onNumberClick(text);
                break;
        }
    }

    private void onNumberClick(String number) {
        currentInput += number;
        display.setText(currentInput);
    }

    private void onOperatorClick(Operator operator) {
        if (!currentInput.isEmpty()) {
            operand1 = Double.parseDouble(currentInput);
            currentInput = "";
        }
        this.operator = operator;
    }

    private void onEqualClick() {
        if (!currentInput.isEmpty() && operator != null) {
            double operand2 = Double.parseDouble(currentInput);
            double result = calculate(operand1, operand2, operator);
            display.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
        }
    }

    private double calculate(double operand1, double operand2, Operator operator) {
        switch (operator) {
            case ADD:
                return operand1 + operand2;
            case SUBTRACT:
                return operand1 - operand2;
            case MULTIPLY:
                return operand1 * operand2;
            case DIVIDE:
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    display.setText("Error");
                    return 0;
                }
            default:
                return 0;
        }
    }
}
