package com.example.dhhs;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class BreakoutActivity extends AppCompatActivity {
    /*
     * The purpose of this class is to have one generic layout for most of the different headings.
     * This way, if more categories are added later no additional code needs to be written, other
     * than if additional functionality is required.
     *
     * Specifically, whichever RecyclerView opened the activity will pass an intent through with the
     * data that needs to be displayed, which is handled in this class.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakout);


        Intent i = getIntent();
        String heading = i.getStringExtra("Heading");
        String info = i.getStringExtra("Info");


        TextView titleText = findViewById(R.id.TitleText);
        TextView contentText = findViewById(R.id.contentText);

        if (titleText == null) {
            Log.e("Tag", "Nulllllll");
        }else{
            Log.e("Tag", "Not Null" + "\n" + titleText);
        }


        titleText.setText(heading);
        contentText.setText(info);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
