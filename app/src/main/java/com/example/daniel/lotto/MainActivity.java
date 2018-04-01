package com.example.daniel.lotto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button sendButton;
    EditText number1, number2, number3, number4, number5, number6;

    int[] intInput = new int[6];
    String[] stringInput = new String[6];
    public  int y;


    public void sendData(View view){

        EditText[] etInput = {number1, number2, number3, number4, number5, number6};

        // Check whether input is empty, or has wrong value, or is out of range
        if(!isEmpty(etInput) && !hasWrongValue(etInput) && isInRange(etInput)){

            // If all values are correct, create intent.
            Intent intent = new Intent(this,ResultsActivity.class);

            // Load all values into INT type array
            for(int i=0;i<etInput.length;i++) {

                intInput[i] = Integer.valueOf(etInput[i].getText().toString());

            }

            // Ascending sorting of INT array
            Arrays.sort(intInput);


            // Check if INT array has repeated numbers in it
            if(!hasRepetitions(intInput)){

                // Send INT array to Result Activity
                intent.putExtra("ValuePCG",intInput);

                // Start Result Activity
                startActivity(intent);

            }
        }
    }

    public boolean isEmpty(EditText[] etInput){

        int counter = 1;

        for(int i=0;i<etInput.length;i++) {

            if (etInput[i].getText().toString().trim().isEmpty()) {

                makeToast("Empty field number "+counter);

                return true;

            }
            counter++;
        }
        return false;
    }

    public boolean hasWrongValue(EditText[] etInput){

        int counter = 1;
        for(int i=0;i<etInput.length;i++) {

            try{

                Integer.parseInt(etInput[i].getText().toString());
                Log.i("MSG",counter+" OK");
                counter++;

            }catch (Exception e){

                makeToast("Wrong input in field number "+counter);
                Log.i("MSG",counter+" WRONG VALUE");
                return true;
            }
        }
        return false;
    }

    public boolean isInRange(EditText[] etInput){

        int counter = 1;

        for(int i=0;i<etInput.length;i++) {
            int value = Integer.valueOf(etInput[i].getText().toString());

            if(value <= 0 || value > 49)
            {
                makeToast("Number in position "+counter+" is out of range.");
                return false;
            }
            counter++;
        }

        return true;
    }

    public boolean hasRepetitions(int[] intValue){

        for(int i = 0 ;i < intValue.length-1; i++){

            if(intValue[i]-intValue[i+1] == 0){
                makeToast("Input has repeated values");
                return true;
            }

        }
        return false;
    }

    public void makeToast(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    private void findAllViews() {
        sendButton = (Button) findViewById(R.id.sendDataButton);
        number1 = (EditText) findViewById(R.id.firstInput);
        number2 = (EditText) findViewById(R.id.secondInput);
        number3 = (EditText) findViewById(R.id.thirdInput);
        number4 = (EditText) findViewById(R.id.fourthInput);
        number5 = (EditText) findViewById(R.id.fifthInput);
        number6 = (EditText) findViewById(R.id.sixthInput);

    }

    public class InputFilterMinMax implements InputFilter {

        private int min,max;

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
                }catch(NumberFormatException nfe){}
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViews();
        setLimiters();
    }
}