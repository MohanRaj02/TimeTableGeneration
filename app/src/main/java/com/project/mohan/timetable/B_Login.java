package com.project.mohan.timetable;

import android.app.AlertDialog;
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

import static android.content.ContentValues.TAG;


public class B_Login extends AsyncTask<String,Void,String> {
    Context context;
    private Task call;
    AlertDialog alertDialog;
    private WeakReference<Task> callback;
    B_Login(Context context) {
        this.context = context;
        this.call = (Task)context;
        this.callback = new WeakReference<>(call);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        String result="";
        String type = args[0];
        String link = "http://mohan02.xyz/Timetable/Login.php";
        if(type.equals("login")){
            String email = args[1];
            String pswd = args[2];
            try {
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("pswd","UTF-8")+"="+URLEncoder.encode(pswd,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result = bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            }
            catch (Exception e) {
                Log.d(TAG,"Exception "+e.toString());
            }
        }
        //Log.d(TAG,"Result"+result);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);
      final Task callBack = callback.get();
      if(callBack!=null)
        call.OnTask(s);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
