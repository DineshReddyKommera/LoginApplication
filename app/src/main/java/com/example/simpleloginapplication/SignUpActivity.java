/*
 * SignUp Activity provides the form for user registration and navigates back to login after submission.
 *  @Author: Dinesh Reddy Kommera (026684084)
 */


package com.example.simpleloginapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;

import java.text.ParseException;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText mTxtUsrName, mTxtPwd, mTxtRetypePwd, mTxtEmail, mTxtPhone;
    private Button mBtnSignUpNow;
    Context context;
    private AwesomeValidation awesomeValidation;
    private HashMap<String, String> usersMap = new HashMap<>();
    String username = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mBtnSignUpNow = (Button) findViewById(R.id.button_signup);
        mTxtUsrName = (EditText) findViewById(R.id.text_usrsignup);
        mTxtPwd = (EditText) findViewById(R.id.text_pwdsignup);
        mTxtRetypePwd = (EditText) findViewById(R.id.text_retypepwd);
        mTxtEmail = (EditText) findViewById(R.id.text_email);
        mTxtPhone = (EditText) findViewById(R.id.text_phone);
        context = getApplicationContext();
        Log.v("SignUp Activity:onCreate", "Checking validation");
        mBtnSignUpNow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validateForm();
            }
        });

    }

    /* Validating different fields of registration*/
    private void validateForm() {
        validateUserName();
        validatePassword();
        validateEmail();
        validatePhone();

        if (awesomeValidation.validate()) {
            Intent main_activity = new Intent(context, MainActivity.class);
            usersMap.put(username, password);
            main_activity.putExtra("usersMap", usersMap);
            setResult(Activity.RESULT_OK, main_activity);
            finish();
        }
    }

    private void validateUserName() {
        Intent intent = getIntent();
        usersMap = (HashMap<String, String>) intent.getSerializableExtra("usersMap");
        username = mTxtUsrName.getText().toString().trim();
        // Custom Validator to check whether the username is unique or not
        awesomeValidation.addValidation(this, R.id.text_usrsignup, new SimpleCustomValidation() {
            @Override
            public boolean compare(String input) {
                if (usersMap.containsKey(input))
                    return false;
                else
                    return true;
            }
        }, R.string.userexists);
        awesomeValidation.addValidation(this, R.id.text_usrsignup, RegexTemplate.NOT_EMPTY, R.string.userempty);
    }

    private void validatePassword() {
        String regexPwd = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[@#$%^&+=]).{4,}";
        awesomeValidation.addValidation(this, R.id.text_pwdsignup, regexPwd, R.string.pwderror);
        password = mTxtPwd.getText().toString().trim();
        String confirmPwd = mTxtRetypePwd.getText().toString().trim();
        if (confirmPwd == null || confirmPwd.length() == 0 || confirmPwd.isEmpty()) {
            awesomeValidation.addValidation(this, R.id.text_retypepwd, RegexTemplate.NOT_EMPTY, R.string.pwdempty);
        } else {
            awesomeValidation.addValidation(this, R.id.text_retypepwd, R.id.text_pwdsignup, R.string.confirmerror);
        }
    }

    private void validateEmail() {
        awesomeValidation.addValidation(this, R.id.text_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
    }

    private void validatePhone() {
        awesomeValidation.addValidation(this, R.id.text_phone, "^[2-9]{1}[0-9]{9}$", R.string.mobileerror);
    }
}