package com.example.dhhs;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class BreakoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakout);

        //MainActivity main = new MainActivity();

        Intent i = getIntent();
        String heading = i.getStringExtra("Heading");
        String info = i.getStringExtra("Info");

        //Log.e("TAG", "Test57\n" + jsonData);

        TextView titleText = findViewById(R.id.TitleText);
        TextView contentText = findViewById(R.id.contentText);

        if (titleText == null) {
            Log.e("Tag", "Nulllllll");
        }else{
            Log.e("Tag", "Not Null" + "\n" + titleText);
        }

        //Log.e("Tag", "yowza\n" + heading);

        titleText.setText(heading);
        contentText.setText(info);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
