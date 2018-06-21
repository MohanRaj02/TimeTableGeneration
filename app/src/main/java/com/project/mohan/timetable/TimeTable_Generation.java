package com.project.mohan.timetable;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import Adapter.Myadapter;
import Model.Adding;

public class TimeTable_Generation extends AppCompatActivity implements Task{


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    static ArrayList<Adding> arrayList = new ArrayList<>();
    static ArrayList<String> staffName = new ArrayList<>();
    static String dept = null,Sem,Sec,id,days,periods;
    private Button generateButton;
    static Random random = new Random();
    public static String check;
    static ProgressDialog progressDialog;
    //private AlertDialog alertDialog;
    static boolean result = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table__generation);

       // alertDialog = new AlertDialog.Builder(TimeTable_Generation.this).create();
        progressDialog = new ProgressDialog(TimeTable_Generation.this);
        dept = null;
        //progressDialog = new ProgressDialog(this);
        //Getting all Details
        Bundle extras = getIntent().getExtras();
        dept = extras.getString("Dept");
        Sem = extras.getString("Sem");
        Sec = extras.getString("Sec");
        days = extras.getString("Days");
        periods = extras.getString("Periods");
        id = extras.getString("Id");

        generateButton = findViewById(R.id.GenerateButton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Generating...");
                progressDialog.show();
                GenerateTT generateTT = new GenerateTT();
                generateTT.generate(id,dept,Sem,Sec,days,periods);
                /*if(result){
                    progressDialog.dismiss();
                    Toast.makeText(TimeTable_Generation.this,"Pdf Generated..",Toast.LENGTH_SHORT).show();
                }*/
                //progressDialog.dismiss();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },1000);
                Toast.makeText(TimeTable_Generation.this,"Pdf Generated..",Toast.LENGTH_SHORT).show();
            }
        });
        staffName.clear();
        arrayList.clear();
        staffName.add("Select Faculty");

        recyclerView = findViewById(R.id.RecyclerViewId);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        B_StaffAdd staffAdd = new B_StaffAdd(TimeTable_Generation.this);
        staffAdd.execute(dept);
        //Toast.makeText(TimeTable_Generation.this,String.valueOf(staffName.size()),Toast.LENGTH_SHORT).show();
        Adding item1 = new Adding("",staffName);
        arrayList.add(item1);
        adding();
    }
    public void adding(){
        adapter = new Myadapter(this, arrayList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_signout){
            Intent intent = new Intent(TimeTable_Generation.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.action_add) {
            Adding item2 = new Adding("",staffName);
            arrayList.add(item2);
            adding();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.subjectadd, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void OnTask(String result) {

    }
    @Override
    public void addStaff(ArrayList<String> staffadd) {
        if(staffadd.get(0).equals("no staff")){
            staffName.add("Not Available");
        }
        else{
            int i=0;
            while(i<staffadd.size()-1){
                staffName.add(staffadd.get(i));
                i++;
            }
        }
    }
    @Override
    public void onCheck(String result) {

    }
    /*public int generateIndex(){
        return random.nextInt(Myadapter.subjects.size());
    }*/
}