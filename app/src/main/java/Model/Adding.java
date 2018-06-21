package Model;


import java.util.ArrayList;

public class Adding {
    private String subjectName;
    private ArrayList<String> staffName;
    public Adding(String subjectName, ArrayList<String> staffName){
        this.subjectName = subjectName;
        this.staffName = staffName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public ArrayList<String> getStaffName() {
        return staffName;
    }

    public void setStaffName(ArrayList<String> staffName) {
        this.staffName = staffName;
    }


}
