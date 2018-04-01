package com.example.daniel.lotto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button sendButton;
    EditText number1, number2, number3, number4, number5, number6;

    ArrayList<Integer> intArray = new ArrayList<>();
    int[] intInput = new int[6];
    String[] stringInput = new String[6];
    public int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViews();
        setLimiters();
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        EditText[] etInput = {number1, number2, number3, number4, number5, number6};

        // Check whether input is empty, or has wrong value, or is out of range
        if (!isEmpty(etInput) && !hasWrongValue(etInput) && isInRange(etInput)) {


            intArray.clear();

            // Load all values into INT type array
            for (EditText etValue : etInput) {

                intArray.add(Integer.valueOf(etValue.getText().toString()));

            }

            // Ascending sorting of INT array
            Arrays.sort(intInput);

            Collections.sort(intArray);

            // Check if INT array has repeated numbers in it
            if (!hasRepetitions(intArray)) {

                // If all values are correct, create intent.
                Intent intent = new Intent(this, ResultsActivity.class);

                // Send INT array to Result Activity
                intent.putIntegerArrayListExtra("ValuePCG" ,intArray);
                //intent.putExtra("ValuePCG", intInput);

                // Start Result Activity
                startActivity(intent);

            }
        }
    }

    public boolean isEmpty(EditText[] etInput) {

        int counter = 1;

        for (int i = 0; i < etInput.length; i++) {

            if (etInput[i].getText().toString().trim().isEmpty()) {

                makeToast("Empty field number " + counter);

                return true;

            }
            counter++;
        }
        return false;
    }

    public boolean hasWrongValue(EditText[] etInput) {

        int counter = 1;
        for (int i = 0; i < etInput.length; i++) {

            try {

                Integer.parseInt(etInput[i].getText().toString());
                Log.i("MSG", counter + " OK");
                counter++;

            } catch (Exception e) {

                makeToast("Wrong input in field number " + counter);
                Log.i("MSG", counter + " WRONG VALUE");
                return true;
            }
        }
        return false;
    }

    public boolean isInRange(EditText[] etInput) {

        int counter = 1;

        for (int i = 0; i < etInput.length; i++) {
            int value = Integer.valueOf(etInput[i].getText().toString());

            if (value <= 0 || value > 49) {
                makeToast("Number in position " + counter + " is out of range.");
                return false;
            }
            counter++;
        }

        return true;
    }

    public boolean hasRepetitions(ArrayList intValue) {

        for (int i = 0; i < intValue.size(); i++) {

            for (int j = i+1 ; j < intValue.size(); j++){

                if( intValue.get(i) == intValue.get(j) ){

                    makeToast("Repeated values");

                    return true;

                }

            }

        }
        return false;
    }

    public void makeToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRanges(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRanges(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    public void setLimiters() {

        final EditText[] inputEt = {number1, number2, number3, number4, number5, number6};

        for (y = 0; y < inputEt.length; y++) {

            inputEt[y].setFilters(new InputFilter[]{new InputFilterMinMax("1", "49")});

        }
    }

    private void findAllViews() {
        sendButton = findViewById(R.id.sendDataButton);
        number1 = findViewById(R.id.firstInput);
        number2 = findViewById(R.id.secondInput);
        number3 = findViewById(R.id.thirdInput);
        number4 = findViewById(R.id.fourthInput);
        number5 = findViewById(R.id.fifthInput);
        number6 = findViewById(R.id.sixthInput);

    }
}