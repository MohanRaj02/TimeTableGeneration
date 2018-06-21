package com.project.mohan.timetable;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mohan.timetable.R;

public class ForgotPassword extends AppCompatActivity {

    private EditText Newpassword,ConfirmPassword;
    private Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Newpassword = findViewById(R.id.NewPasswordText);
        ConfirmPassword = findViewById(R.id.ConfirmPasswordText);
        change =findViewById(R.id.ChangeButton);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForgotPassword.this,"Forgot",Toast.LENGTH_SHORT).show();
            }
        });
    }
}