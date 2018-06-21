package com.project.mohan.timetable;

import java.util.ArrayList;

/**
 * Created by user on 29-03-2018.
 */

public interface Task{
    void OnTask(String result);
    void addStaff(ArrayList<String> staffadd);
    void onCheck(String result);
}