package com.project.mohan.timetable;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class B_CheckStaff extends AsyncTask<String,Void,String>  {
   /*Context context;
    private Task call;*/
    B_CheckStaff(){
    }
    @Override
    protected String doInBackground(String... args) {
        String row = args[0];
        String col = args[1];
        String name = args[2];
        String result="";
        String link = "http://mohan02.xyz/Timetable/Check.php";
        try{
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8");
            data+="&"+URLEncoder.encode("row","UTF-8")+"="+URLEncoder.encode(row,"UTF-8");
            data+="&"+URLEncoder.encode("col","UTF-8")+"="+URLEncoder.encode(col,"UTF-8");
            bufferedWriter.write(data);
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
        catch(Exception e){

        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //call.onCheck(result);
        super.onPostExecute(result);
        //GenerateTT.check = result;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
