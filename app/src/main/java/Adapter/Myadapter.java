package Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mohan.timetable.R;
import java.util.ArrayList;

import Model.Adding;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
    String subject=null;
    int i,j;
    private Context context;
    public static ArrayList<Adding> arrayList = new ArrayList<>();
    public static ArrayList<String> subjects = new ArrayList<>();
    public static ArrayList<String> staffname = new ArrayList<>();
    public Myadapter(){
        i=0;
        j=0;
    }
    public Myadapter(Context context, ArrayList<Adding> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adding,parent,false);
        return new ViewHolder(view);
    }

    public void addingSubject(int i,String subjectName){
        if(i==subjects.size())
            subjects.add(" ");
        subjects.set(i,subjectName);
    }
    public void addingStaff(int i,String staffName){
        if(i==staffname.size())
            staffname.add(" ");
        staffname.set(i,staffName);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            subject = null;
            Adding adding = arrayList.get(position);
            holder.subjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                subject = s.toString();
                addingSubject(position,subject);
            }
        });
            ArrayAdapter<String> staffAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, adding.getStaffName());
            staffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.staffName.setAdapter(staffAdapter);
            if(j<staffname.size()){
                int pos = staffAdapter.getPosition(staffname.get(j));
                holder.staffName.setSelection(pos);
                //Toast.makeText(context,staffname.get(j),Toast.LENGTH_SHORT).show();
                j++;
            }
            holder.staffName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    String selected = parent.getItemAtPosition(i).toString();
                    if(!selected.equals("Select Faculty")) {
                        //Toast.makeText(context,String.valueOf(parent.getId()), Toast.LENGTH_SHORT).show();
                        addingStaff(position,selected);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        public  EditText subjectName;
        public  Spinner staffName;
        public ViewHolder(View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.SubjectName);
            if(i<subjects.size()){
                subjectName.setText(subjects.get(i));
                i++;
            }
            staffName = itemView.findViewById(R.id.staff);
        }
    }
}