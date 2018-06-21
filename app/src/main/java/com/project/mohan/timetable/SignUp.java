package com.project.mohan.timetable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mohan.timetable.R;

import java.util.ArrayList;

public  class SignUp extends AppCompatActivity implements Task{
    private EditText Name,Department,Mobile,Email,Password;
    private Button Register;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progress = new ProgressDialog(this);

        Name = findViewById(R.id.StaffNameText);
        Department = findViewById(R.id.DepartmentText);
        Mobile = findViewById(R.id.MobileText);
        Email = findViewById(R.id.EmailText);
        Password = findViewById(R.id.PasswordText);
        Register = findViewById(R.id.RegisterButton);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String staffname = Name.getText().toString();
                String dept = Department.getText().toString();
                String phone = Mobile.getText().toString();
                String mail = Email.getText().toString();
                String pswd = Password.getText().toString();
                if(!staffname.equals("")&&!dept.equals("")&&!phone.equals("")&&!mail.equals("")&&!pswd.equals("")) {
                    progress.setMessage("Registering...");
                    progress.show();
                    String type = "signup";
                    B_SignUp bSignUp = new B_SignUp(SignUp.this);
                    bSignUp.execute(type,staffname,dept,phone,mail,pswd);
                }
                else{
                    Toast.makeText(SignUp.this,"Fill All Details",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void OnTask(String result) {
        if(result.equals("Inserted")) {
         //   startActivity(new Intent(SignUp.this, MainActivity.class));
            Intent returnIntent = getIntent();
            returnIntent.putExtra("Msg","Account Created!!!");
            setResult(RESULT_OK,returnIntent);
            finish();
        }
        else {
            progress.dismiss();
            Toast.makeText(SignUp.this, "Error  ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addStaff(ArrayList<String> staffname) {

    }

    @Override
    public void onCheck(String result) {

    }


}
