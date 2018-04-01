package com.example.daniel.lotto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Arrays;
import java.util.Random;

public class ResultsActivity extends AppCompatActivity {

    private Button backButton,reloadButton;
    private TextView usersNumber1, usersNumber2, usersNumber3, usersNumber4, usersNumber5, usersNumber6;

    private int[] usersNumbers = new int[6];
    private int[] resultNumbers = new int[6];
    private int[] Randomize = new int[6];
    private TextView resultNumber1,resultNumber2,resultNumber3,resultNumber4,resultNumber5,resultNumber6;


    private void findAllViews() {

        //Finding all usable views
        backButton = (Button) findViewById(R.id.backButton);
        reloadButton = (Button) findViewById(R.id.reloadButton);

        usersNumber1 = (TextView) findViewById(R.id.firstInput);
        usersNumber2 = (TextView) findViewById(R.id.secondInput);
        usersNumber3 = (TextView) findViewById(R.id.thirdInput);
        usersNumber4 = (TextView) findViewById(R.id.fourthInput);
        usersNumber5 = (TextView) findViewById(R.id.fifthInput);
        usersNumber6 = (TextView) findViewById(R.id.sixthInput);

        resultNumber1 = (TextView) findViewById(R.id.firstResult);
        resultNumber2 = (TextView) findViewById(R.id.secondResult);
        resultNumber3 = (TextView) findViewById(R.id.thirdResult);
        resultNumber4 = (TextView) findViewById(R.id.fourthResult);
        resultNumber5 = (TextView) findViewById(R.id.fifthResult);
        resultNumber6 = (TextView) findViewById(R.id.sixthResult);
    }

    private void unpackIntent(){

        // Creating TextView array that holds privided users numbers from MainActivity
        TextView[] tvNumbers =  {usersNumber1, usersNumber2, usersNumber3, usersNumber4, usersNumber5, usersNumber6};

        // Unpacking values sent from MainActivity
        usersNumbers = getIntent().getIntArrayExtra("ValuePCG");

        // Log retreived numbers
        for(int v : usersNumbers){

            Log.i("MSG", String.valueOf(v));

        }

        // Setting retreived numbers to TextView fields [UsersNumber1 ... ]
        for(int i = 0 ; i < usersNumbers.length ; i++){
            tvNumbers[i].setText(String.valueOf(usersNumbers[i]));
        }
    }

    public void goBack(View view){

        // Creating new intent that leads to MainActivity
        Intent goBack = new Intent(ResultsActivity.this,MainActivity.class);

        // Starts MainActivity
        startActivity(goBack);
    }

    public void reload(View view){
        cleanOldWinners();
        randomizer();
        winChecker();
    }

    private void randomizer() {

        TextView[] randNumbers =  {resultNumber1, resultNumber2, resultNumber3, resultNumber4, resultNumber5, resultNumber6};


        Random rand = new Random();

        // Randomizing an array of numbers
        for(int i = 0; i < 6; i++)
        {
            Randomize[i] =  rand.nextInt(49)+1;
        }

        // Ascending sorting of randomized numbers
        Arrays.sort(Randomize);


        if(hasRepetitions(Randomize)){

            randomizer();

        }else{

            // Setting randomized numbers
            for(int i = 0 ; i < resultNumbers.length ; i++){

                randNumbers[i].setText(String.valueOf(Randomize[i]));

            }

        }
    }

    public boolean hasRepetitions(int[] intValue){

        for(int i = 0 ;i < intValue.length-1; i++){

            if(intValue[i]-intValue[i+1] == 0){

                //Input has repeated values

                return true;

            }
        }
        return false;
    }

    public void winChecker(){

        int matches = 0;
        int[] matchingNumbers = new int[6];
        TextView[] correctNumbers =  {usersNumber1, usersNumber2, usersNumber3, usersNumber4, usersNumber5, usersNumber6};


        for(int i = 0;i < usersNumbers.length ; i++){

            for(int j = 0; j< usersNumbers.length ; j++){

                if(usersNumbers[i] == Randomize[j]){
                    matchingNumbers[matches]=Randomize[j];
                    matches++;
                    correctNumbers[i].setTextColor(getResources().getColor(R.color.winColor));
                }

            }

        }
        Log.i("MSG", "Matches :  "+matches+" Matching nums : "+Arrays.toString(matchingNumbers));

    }

    public void cleanOldWinners(){
        TextView[] correctNumbers =  {usersNumber1, usersNumber2, usersNumber3, usersNumber4, usersNumber5, usersNumber6};

        for(int i=0;i<6;i++){
            correctNumbers[i].setTextColor(getResources().getColor(R.color.cleanColor));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        findAllViews();
        unpackIntent();
        reload(usersNumber1);
    }
}
