package com.project.mohan.timetable;

import android.app.Activity;
import android.os.Environment;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import Adapter.Myadapter;

public class GenerateTT extends Activity {
    GenerateTT(){

    }
    //String id,Dept,Sem,Sec,r,c;
    //public static final String dest = Environment.getExternalStorageDirectory()+"/mypdf.pdf";
    public static String check;
    static Random random = new Random();

    static int generateIndex(){
        return random.nextInt(Myadapter.subjects.size());
    }
    public void generate(String id, String Dept, String Sem, String Sec, String r, String c)  {
        int i=0;
        int row = Integer.parseInt(r)+1;
        int col = Integer.parseInt(c)+1;
        String days[] = {"MON","TUE","WED","THU","FRI","SAT"};
        String tt[][] = new String[row][col];
        tt[0][0]="Periods"+"\n"+"Days";
        int rf=0,cf=1;

        String filename = Dept+" "+Sem+"-Sem "+Sec+"-Section";
        //Pdf creation
        //PdfDocument document = new PdfDocument();
        //  document.setMargins(1,1,1,1);
        //document.addTitle("TimeTable");
        File Path = new File(Environment.getExternalStorageDirectory()+"/Timetable");
        if(!Path.exists()){
            Path.mkdir();
        }
       File  pdfFile = new File(Path,filename+".pdf");
        Document document = new Document();
        //String Path = Environment.getExternalStorageDirectory()+"/"+filename+".pdf";
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        PdfPTable table = new PdfPTable(col);
        //table.getFittingRows(5,0);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        int j=1;
        i=0;
        while(j<col){
            tt[i][j] = String.valueOf(cf);
            j++;
            cf++;
        }

        //Fill Days
        i=1;
        j=0;
        while(i<row){
            tt[i][j] = days[rf];
            rf++;
            i++;
        }

        //Fill Subject
        i=1;
        while(i<row){
            j=1;
            while(j<col){
                int index = generateIndex();
                check=null;
                String subject = Myadapter.subjects.get(index);
                B_CheckStaff b_checkStaff = new B_CheckStaff();
                try {
                    check = b_checkStaff.execute(String.valueOf(i),String.valueOf(j),Myadapter.staffname.get(index)).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(((j==1)&&check.equals("True"))||(check.equals("True")&&(!tt[i][j-1].equals(subject)))){
                    tt[i][j]=subject;
                    j++;
                }
            }
            i++;
        }
        for(String[]ro:tt){
            for(String co:ro) {
                table.addCell(co);
            }
        }
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        /*try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }*/
        Paragraph paragraph = new Paragraph();
        i=0;
        while(i<Myadapter.subjects.size()){
            paragraph.add(Myadapter.subjects.get(i)+"-"+Myadapter.staffname.get(i)+"\n");
            i++;
        }
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
       // return true;
    }
}