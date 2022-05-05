package com.example.bossraise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class GameScreen extends AppCompatActivity {

    TextView question, description;
    Button op1, op2, op3;
    DecisionMap map;
    DecisionNode node;
    Inventory inv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        question = (TextView)findViewById(R.id.question);
        description = (TextView)findViewById(R.id.description);
        op1 = (Button)findViewById(R.id.option1);
        op2 = (Button)findViewById(R.id.option2);
        op3 = (Button)findViewById(R.id.option3);

        try {
            map = new DecisionMap(getApplicationContext());

            inv = new Inventory();
            inv.setPhotos(0);
            inv.setCoffee(0);

            node = map.entryPoint();
            changeText(node);

        } catch(Resources.NotFoundException e)  {
            Log.e("CSV not found", "CSV file was not found. " + e.getMessage());
            exceptionToast(getApplicationContext(), e.getMessage());
            finish();
        }  catch(CSVFormatError | invalidCSVitem e)  {
            Log.e("CSV item error", "Invalid CSV argument found. " + e.getMessage());
            exceptionToast(getApplicationContext(), e.getMessage());
            finish();
        } catch(IOException e)  {
            Log.e("Logic error", "Issue with CSV file data. " + e.getMessage());
            exceptionToast(getApplicationContext(), e.getMessage());
            finish();
        } catch(Exception e)  {
            Log.e("Unhandled Exception", e.getMessage());
            exceptionToast(getApplicationContext(), e.getMessage());
            finish();
        }


    }

    // navigates map and changes text accordingly
    public void changeText(DecisionNode myNode)  {

        node = myNode;
        String qstn = node.getQuestion();
        String desc = node.getDescription();
        // clean up GUI
        hideButton(node);


        // checks if current node adds an item to player inventory
        if (qstn.equals("This will come in handy"))  {
            getItem(desc);
        }

        // checks if current node is decision node
        if (qstn.equals("DECISION"))  {
            decisionCheck(node, desc);
            return;
        }

        // changes text based on button pressed
        question.setText(qstn);
        description.setText(desc);
        op1.setText(myNode.getOp1Description());
        op2.setText(myNode.getOp2Description());
        op3.setText(myNode.getOp3Description());
    }


    // changes text according to button press
    public void button1(View view)  {
        // Go back to title screen if game is over
        if (node.getOp1Description().equals("Play again"))  {
            goTitleScreen(); }

        changeText(node.getOption1());
    }
    public void button2(View view)  { changeText(node.getOption2()); }
    public void button3(View view)  { changeText(node.getOption3()); }


    // changes decision node text automatically according to player inventory
    public void decisionCheck(DecisionNode node, String item)  {
        if (item.equals("COFFEE")) {
            if (inv.checkCoffee()) {
                // use coffee
                changeText(node.getOption2());
                // remove coffee from inventory
                inv.setCoffee(0);
            } else {
                changeText(node.getOption1());
            }
        } else if (item.equals("PHOTOS"))  {
            if (inv.checkPhotos()) {
                // use photos
                changeText(node.getOption2());
                // remove photos from inventory
                inv.setPhotos(0);
            } else {
                changeText(node.getOption1());
            }
        }
    }

    // adds item to player inventory
    public void getItem(String item)  {
        if (item.equals("You got Coffee!")) {
            inv.setCoffee(1);
        } else if (item.equals("You got Mr X's Explicit photos!")) {
            inv.setPhotos(1);
        }

    }

    // cleans up GUI by hiding needless buttons
    public void hideButton(DecisionNode node)  {
        if (node.getOp2Description().equals("-")) {
            op2.setVisibility(View.INVISIBLE);
        } else {
            op2.setVisibility(View.VISIBLE);
        }
        if (node.getOp3Description().equals("-")) {
            op3.setVisibility(View.INVISIBLE);
        } else {
            op3.setVisibility(View.VISIBLE);
        }

    }

    public void goTitleScreen()  {
        Intent titleScreen = new Intent(this, TitleScreen.class);
        startActivity(titleScreen);
    }

    public static void exceptionToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}