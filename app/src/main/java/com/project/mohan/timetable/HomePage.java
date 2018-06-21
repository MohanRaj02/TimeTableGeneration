package com.project.mohan.timetable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mohan.timetable.R;

import java.util.ArrayList;

import Adapter.Myadapter;

public class HomePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static ArrayList<String> department = new ArrayList<>();
    static ArrayList<String> semester = new ArrayList<>();
    static ArrayList<String> section  = new ArrayList<>();
    static String sdept,ssem,ssec,sdays,speriods;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        sdept = null;
        ssem = null;
        ssec = null;
        sdays = null;
        speriods = null;

        department.clear();
        semester.clear();
        section.clear();
        Spinner dept = findViewById(R.id.dept);
        Spinner sem = findViewById(R.id.sem);
        Spinner sec = findViewById(R.id.sec);
        final TextView daysText = findViewById(R.id.workingDay);
        final TextView periodsText = findViewById(R.id.Periods);
        Button nextButton = findViewById(R.id.Next);

        dept.setOnItemSelectedListener(HomePage.this);
        sem.setOnItemSelectedListener(HomePage.this);
        sec.setOnItemSelectedListener(HomePage.this);

        //get id
        Bundle extra = getIntent().getExtras();
        id = extra.getString("Id").toString();
        //Department
        department.add("Department");
        department.add("CSE");
        department.add("IT");
        department.add("ECE");
        department.add("EEE");
        department.add("MECH");
        department.add("CIVIL");
        department.add("MCT");

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,department);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(deptAdapter);
        dept.setSelection(0);

        //semester

        semester.add("Semester");
        semester.add("I");
        semester.add("II");
        semester.add("III");
        semester.add("IV");
        semester.add("V");
        semester.add("VI");
        semester.add("VII");
        semester.add("VIII");

        ArrayAdapter<String> semAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,semester);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(semAdapter);
        sem.setSelection(0);

        //Section
        section.add("Section");
        section.add("A");
        section.add("B");
        section.add("C");

        ArrayAdapter<String> secAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,section);
        secAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sec.setAdapter(secAdapter);
        sec.setSelection(0);


        //NextPage
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdays = daysText.getText().toString();
                speriods =periodsText.getText().toString();
                if(sdept!=null&&ssem!=null&&ssec!=null&&!sdays.equals("")&&!speriods.equals("")){
                    //Intent intent = new Intent(HomePage.this,TTGenerate.class);
                    TimeTable_Generation.staffName.clear();
                    TimeTable_Generation.arrayList.clear();
                    Myadapter.staffname.clear();
                    Myadapter.arrayList.clear();
                    Myadapter.subjects.clear();
                    Intent intent = new Intent(HomePage.this,TimeTable_Generation.class);
                    intent.putExtra("Dept",sdept);
                    intent.putExtra("Sem",ssem);
                    intent.putExtra("Sec",ssec);
                    intent.putExtra("Days",sdays);
                    intent.putExtra("Periods",speriods);
                    intent.putExtra("Id",id);
                    //startActivity(new Intent(HomePage.this,TTGenerate.class));
                    startActivity(intent);
                    //finishActivityFromChild(HomePage.this,1);
                }
                else{
                    Toast.makeText(HomePage.this,"Fill All Details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_signout){
            startActivity(new Intent(HomePage.this,MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selected =   adapterView.getItemAtPosition(i).toString();
        if(selected.equals("Department"))
            sdept = null;
        if(selected.equals("Semester"))
            ssem = null;
        if(selected.equals("Section"))
            ssec = null;
        if(department.contains(selected)&&!selected.equals("Department")){
            sdept = selected;
            //Toast.makeText(this,selected,Toast.LENGTH_SHORT).show();
        }
        else if(semester.contains(selected)&&!selected.equals("Semester")){
            ssem = selected;
            //Toast.makeText(this,selected,Toast.LENGTH_SHORT).show();
        }
        else if(section.contains(selected)&&!selected.equals("Section")){
            ssec = selected;
            //Toast.makeText(this,selected,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
