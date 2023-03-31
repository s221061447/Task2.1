package com.application.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Length Conversions
    static final double inchToCm = 2.54;
    static final double footToCm = 30.48;
    static final double yardToCm = 91.44;
    static final double mileToCm = 160934;

    // Weight Conversions
    static final double poundToGrams = 453.592;
    static final double ounceToGrams = 28.3495;
    static final double tonToGrams = 907185;

    static final NumberFormat formatter = new DecimalFormat("#0.0000000");

    EditText sourceValue;
    TextView convertedValue;
    Spinner measurementType, sourceUnit, destinationUnit;
    Button convertButton;

    ArrayAdapter<CharSequence> getUnitAdapter(Context context, int textArrayResId) {
        ArrayAdapter<CharSequence> unitArrayAdapter;
        unitArrayAdapter = ArrayAdapter.createFromResource(context, textArrayResId, android.R.layout.simple_spinner_item);
        unitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return unitArrayAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceValue = findViewById(R.id.sourceValue);
        convertedValue = findViewById(R.id.convertedValue);
        measurementType = findViewById(R.id.measurementType);
        sourceUnit = findViewById(R.id.sourceUnit);
        destinationUnit = findViewById(R.id.destinationUnit);
        convertButton = findViewById(R.id.convertButton);

        measurementType.setAdapter(getUnitAdapter(this, R.array.conversions));
        measurementType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String choice = adapterView.getItemAtPosition(i).toString();

                switch(choice) {
                    case "Length":
                        ArrayAdapter<CharSequence> lengthUnitAdapter = getUnitAdapter(getApplicationContext(), R.array.lengthConversions);
                        sourceUnit.setAdapter(lengthUnitAdapter);
                        destinationUnit.setAdapter(lengthUnitAdapter);
                        destinationUnit.setSelection(1);
                        break;
                    case "Weight":
                        ArrayAdapter<CharSequence> weightUnitAdapter = getUnitAdapter(getApplicationContext(), R.array.weightConversions);
                        sourceUnit.setAdapter(weightUnitAdapter);
                        destinationUnit.setAdapter(weightUnitAdapter);
                        destinationUnit.setSelection(1);
                        break;
                    case "Temperature":
                        ArrayAdapter<CharSequence> temperatureUnitAdapter = getUnitAdapter(getApplicationContext(), R.array.temperatureConversions);
                        sourceUnit.setAdapter(temperatureUnitAdapter);
                        destinationUnit.setAdapter(temperatureUnitAdapter);
                        destinationUnit.setSelection(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        convertButton.setOnClickListener(view -> {
            String preValidatedValue = sourceValue.getText().toString();
            Double value = getDouble(preValidatedValue);
            double finalValue;
            if (value == null) {
                Toast.makeText(MainActivity.this, "Invalid source value!", Toast.LENGTH_LONG).show();
                return;
            }
            if (sourceUnit.getSelectedItem() == destinationUnit.getSelectedItem()) {
                Toast.makeText(MainActivity.this, "Source and destination unit is the same!", Toast.LENGTH_LONG).show();
                return;
            }

            switch(measurementType.getSelectedItem().toString()) {
                case "Length":
                    finalValue = performLengthConversion(sourceUnit.getSelectedItem().toString(),
                            destinationUnit.getSelectedItem().toString(),
                            value);
                    break;
                case "Weight":
                    finalValue = performWeightConversion(sourceUnit.getSelectedItem().toString(),
                            destinationUnit.getSelectedItem().toString(),
                            value);
                    break;
                case "Temperature":
                    finalValue = performTemperatureConversion(sourceUnit.getSelectedItem().toString(),
                            destinationUnit.getSelectedItem().toString(),
                            value);
                    break;
                default:
                    Toast.makeText(MainActivity.this, "Invalid measurement type!", Toast.LENGTH_LONG).show();
                    return;
            }
            convertedValue.setText(formatter.format(finalValue));
        });

    }

    Double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch(NumberFormatException e) {
            return null;
        }
    }

    double performLengthConversion(String sourceUnit, String destinationUnit, double value) {
        double sourceInCm;
        double finalValue;
        switch(sourceUnit) {
            case "Inch":
                sourceInCm = value * inchToCm;
                break;
            case "Foot":
                sourceInCm = value * footToCm;
                break;
            case "Yard":
                sourceInCm = value * yardToCm;
                break;
            case "Mile":
                sourceInCm = value * mileToCm;
                break;
            default:
                sourceInCm = 1;
                break;
        }
        switch(destinationUnit) {
            case "Inch":
                finalValue = sourceInCm/inchToCm;
                break;
            case "Foot":
                finalValue = sourceInCm/footToCm;
                break;
            case "Yard":
                finalValue = sourceInCm/yardToCm;
                break;
            case "Mile":
                finalValue = sourceInCm/mileToCm;
                break;
            default:
                finalValue = 1;
                break;
        }
        return finalValue;
    }

    double performWeightConversion(String sourceUnit, String destinationUnit, double value) {
        double sourceInGrams;
        double finalValue;
        switch(sourceUnit) {
            case "Pound":
                sourceInGrams = value * poundToGrams;
                break;
            case "Ounce":
                sourceInGrams = value * ounceToGrams;
                break;
            case "Ton":
                sourceInGrams = value * tonToGrams;
                break;
            default:
                sourceInGrams = 1;
                break;
        }
        switch(destinationUnit) {
            case "Pound":
                finalValue = sourceInGrams/poundToGrams;
                break;
            case "Ounce":
                finalValue = sourceInGrams/ounceToGrams;
                break;
            case "Ton":
                finalValue = sourceInGrams/tonToGrams;
                break;
            default:
                finalValue = 1;
                break;
        }
        return finalValue;
    }

    double performTemperatureConversion(String sourceUnit, String destinationUnit, double value) {
        double sourceInCelsius;
        double finalValue;
        switch(sourceUnit) {
            case "Celsius":
                sourceInCelsius = value;
                break;
            case "Fahrenheit":
                sourceInCelsius = (value - 32) / 1.8;
                break;
            case "Kelvin":
                sourceInCelsius = value - 273.15;
                break;
            default:
                sourceInCelsius = 1;
                break;
        }
        switch(destinationUnit) {
            case "Celsius":
                finalValue = sourceInCelsius;
                break;
            case "Fahrenheit":
                finalValue = (sourceInCelsius * 1.8) + 32;
                break;
            case "Kelvin":
                finalValue = sourceInCelsius + 273.15;
                break;
            default:
                finalValue = 1;
                break;
        }
        return finalValue;
    }
}