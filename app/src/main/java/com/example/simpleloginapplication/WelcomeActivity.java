/*
 * Welcome Activity which displays the welcome message after successful login
 *  @Author: Dinesh Reddy Kommera (026684084)
 */

package com.example.simpleloginapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {

    private Button mBtnLogOut;
    private TextView mUserName;
    Context context;
    private HashMap<String, String> usersMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mBtnLogOut = findViewById(R.id.button_logout);
        mUserName = findViewById(R.id.txt_welcome);
        context = getApplicationContext();
        Intent intent = getIntent();
        usersMap = (HashMap<String, String>) intent.getSerializableExtra("usersMap");
        String username = (String) intent.getStringExtra("username");
        Log.v("WelcomeActivity: Logged User :", username);
        mUserName.setText("Welcome " + username + "!");
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Getting back to login page
                Intent main_activity = new Intent(context, MainActivity.class);
                main_activity.putExtra("usersMap", usersMap);
                setResult(Activity.RESULT_OK, main_activity);
                finish();

            }
        });
    }
}
