package com.project.mohan.timetable;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mohan.timetable.R;

import java.util.ArrayList;

import Adapter.Myadapter;


public  class MainActivity extends AppCompatActivity implements Task {

    private EditText mail, password;
    private TextView forgotPassword;
    private Button login, signup;
    private ProgressDialog progress;
    private final int REQUEST_CODE = 23;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = new ProgressDialog(this);

        mail = findViewById(R.id.EmailText);
        password = findViewById(R.id.PasswordText);
        login = findViewById(R.id.LoginButton);
        signup = findViewById(R.id.SignUpButton);
        forgotPassword = findViewById(R.id.ChangeButton);

        alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        //Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString().trim();
                String pswd = password.getText().toString();
                String type = "login";
                if(!email.equals("")&&!pswd.equals("")&&checkConnection()){
                    progress.setMessage("Logging in...");
                    progress.show();
                    B_Login BLogin = new B_Login(MainActivity.this);
                    BLogin.execute(type,email,pswd);
                }
                else if(email.equals("")&&pswd.equals("")){
                    Toast.makeText(MainActivity.this,"Fill All Details",Toast.LENGTH_SHORT).show();
                }
                else if(!checkConnection()){
                    alertDialog.setTitle("WARNING!!");
                    alertDialog.setMessage("Network Not Connected");
                    alertDialog.show();
                }
            }
        });


        //Signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnection()) {
                    startActivityForResult(new Intent(MainActivity.this, SignUp.class), REQUEST_CODE);
                }
                else{
                    alertDialog.setTitle("Warning!!!");
                    alertDialog.setMessage("Network Not Connected");
                    alertDialog.show();
                }
            }
        });

        //Forgot Password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailtxt = mail.getText().toString();
                if(!mailtxt.equals(""))
                    startActivity(new Intent(MainActivity.this,ForgotPassword.class));
                else
                    Toast.makeText(MainActivity.this,"Fill Email",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void OnTask(String result) {
        if(!result.equals("Failed")&&!result.equals("")) {
            progress.dismiss();
            Myadapter.staffname.clear();
            Myadapter.arrayList.clear();
            Myadapter.subjects.clear();
            TimeTable_Generation.arrayList.clear();
            TimeTable_Generation.staffName.clear();
            //Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,HomePage.class);
            //int id = Integer.parseInt(result);
            intent.putExtra("Id",result);
            //intent.putExtra("Id",id);
            //startActivity(new Intent(MainActivity.this,HomePage.class));
            startActivity(intent);
            finish();
        }
        else{
            progress.dismiss();
            Toast.makeText(MainActivity.this,"Invalid",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addStaff(ArrayList<String> staffname) {

    }

    @Override
    public void onCheck(String result) {

    }


    boolean checkConnection(){
        ConnectivityManager conn = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if ( conn.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                conn.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                conn.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                conn.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK) {
                String result = data.getStringExtra("Msg");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
}