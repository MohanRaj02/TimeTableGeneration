package com.project.mohan.timetable;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class B_StaffAdd extends AsyncTask<String ,Void,ArrayList<String>> {
    Context context;
    private Task call;
    private WeakReference<Task> callback;
    public B_StaffAdd(Context context) {
        this.context = context;
        this.call =(Task)context;
        this.callback = new WeakReference<>(call);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(String... args) {
        ArrayList<String> staffname = new ArrayList<>();
        String link = "http://mohan02.xyz/Timetable/StaffAdd.php";
        String get="";
        try{
            String dept = args[0].toLowerCase();
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("dept","UTF-8")+"="+URLEncoder.encode(dept,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            while((get=bufferedReader.readLine())!=null){
                String result = get.toString();
                staffname.add(result);
            }
            /*get = bufferedReader.readLine();
            staffname.add(get);*/
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        }
        catch(Exception e){
            Log.d(TAG,"Exception "+e.toString());
        }
        return staffname;
    }

    @Override
    protected void onPostExecute(ArrayList<String> staffname) {
        super.onPostExecute(staffname);
        final Task callBack = callback.get();
        if(callBack!=null)
            call.addStaff(staffname);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
