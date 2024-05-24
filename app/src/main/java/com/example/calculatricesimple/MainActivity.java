package com.example.calculatricesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Affichage des chiffres et résultats
    private TextView display;
    // Ce que l'utilisateur tape
    private String currentInput = "";
    // Opérateur choisi (+, -, *, /)
    private Operator operator = null;
    // Premier nombre pour le calcul
    private double operand1 = 0;

    // Les opérateurs disponibles
    private enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    // Appelé quand l'activité est créée
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser l'affichage
        display = findViewById(R.id.display);
        // Configurer les boutons
        setupButtons();
    }

    // Configurer les boutons pour les clics
    private void setupButtons() {
        // Liste des boutons
        int[] buttonIDs = {
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEqual, R.id.buttonClear
        };

        // Ajouter un clic pour chaque bouton
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

    // Gérer le clic sur un bouton
    private void onButtonClick(Button button) {
        String text = button.getText().toString();
        switch (text) {
            case "C":
                // Réinitialiser
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

    // Ajouter un chiffre
    private void onNumberClick(String number) {
        currentInput += number;
        display.setText(currentInput);
    }

    // Ajouter un opérateur
    private void onOperatorClick(Operator operator) {
        if (!currentInput.isEmpty()) {
            if (this.operator != null) {
                double operand2 = Double.parseDouble(currentInput);
                operand1 = calculate(operand1, operand2, this.operator);
                display.setText(String.valueOf(operand1));
            } else {
                operand1 = Double.parseDouble(currentInput);
            }
            currentInput = "";
        }
        this.operator = operator;
    }

    // Calculer le résultat
    private void onEqualClick() {
        if (!currentInput.isEmpty() && operator != null) {
            double operand2 = Double.parseDouble(currentInput);
            double result = calculate(operand1, operand2, operator);
            display.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
            operator = null;
        }
    }

    // Faire le calcul selon l'opérateur
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
