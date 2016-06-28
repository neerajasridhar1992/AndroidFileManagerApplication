package com.example.niveda.filemanager;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;*/

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends ActionBarActivity {
    private Socket client;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private OutputStream outputStream;
    private InputStream inputStream;

    private byte[] filename=new byte[256];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        selectFile();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void selectFile() {
        String state = Environment.getExternalStorageState();

        if (!(state.equals(Environment.MEDIA_MOUNTED))) {
            Toast.makeText(this, "There is no any sd card", Toast.LENGTH_LONG).show();


        } else {
            BufferedReader reader = null;
            // try {
            Toast.makeText(this, "Sd card available", Toast.LENGTH_LONG).show();
            Log.d("path", "");
            String str= new String();
            File file = Environment.getExternalStorageDirectory();
            File textFile = new File(file.getAbsolutePath()+"/SyncPic" + File.separator);
            final File[] text = textFile.listFiles();
            TextView name1 = (TextView) findViewById(R.id.FileName1);
            str= String.valueOf(text[0]).substring(String.valueOf(text[0]).lastIndexOf("/"),String.valueOf(text[0]).length());
            name1.setText(str);
            Log.d("path", String.valueOf(text[0]));
            str= String.valueOf(text[1]).substring(String.valueOf(text[1]).lastIndexOf("/"),String.valueOf(text[1]).length());
            TextView name2 = (TextView) findViewById(R.id.FileName2);
            name2.setText(str);
            Log.d("path", String.valueOf(text[1]));
            TextView name3 = (TextView) findViewById(R.id.FileName3);
            str= String.valueOf(text[2]).substring(String.valueOf(text[2]).lastIndexOf("/"),String.valueOf(text[2]).length());
            name3.setText(str);
            Log.d("path", String.valueOf(text[2]));
            name1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serverConnect(String.valueOf(text[0]));
                    Toast.makeText(getApplicationContext(), String.valueOf(text[0]), Toast.LENGTH_LONG).show();
                }
            });

            name2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serverConnect(String.valueOf(text[1]));
                    Toast.makeText(getApplicationContext(), String.valueOf(text[1]), Toast.LENGTH_LONG).show();
                }
            });

            name3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serverConnect(String.valueOf(text[2]));
                    Toast.makeText(getApplicationContext(), String.valueOf(text[2]), Toast.LENGTH_LONG).show();
                }
            });



        }
    }

    private void serverConnect(String fileName) {
        File file = new File(fileName); //create file instance
        System.out.println("in here 111" + file.getName());
        try {

            client = new Socket("52.53.195.139", 7891);
            System.out.println("in here 2");
            outputStream = client.getOutputStream();
            System.out.println("after creating outputstream");
            DataOutputStream dop=new DataOutputStream(outputStream);
            dop.writeUTF(file.getName());
            // outputStream.write(file.getName().getBytes());
            System.out.println("after sending filename");
            inputStream=client.getInputStream();
            System.out.println("after creating inputstream");
            DataInputStream dataInput=new DataInputStream(inputStream);
            System.out.println("after creating inputstream");
            int fl=dataInput.read(filename,0,3);
                    /*int bytesRead=inputStream.read(filename,0,filename.length);
                    System.out.println("bytesRead:" + bytesRead);
                    filename[bytesRead]=0;*/
            System.out.println("fl:"+fl);
            String getOutput=new String(filename);
            System.out.println("filename:"+getOutput.substring(0,3));
            String newOutput=getOutput.substring(0,3);

            if (newOutput.equals("YES")) {
                System.out.println("after getting YES");

                byte[] mybytearray = new byte[(int) file.length()]; //create a byte array to file
                System.out.println("in here 3:" + mybytearray.length);

                fileInputStream = new FileInputStream(file);
                System.out.println("in here 777");
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                System.out.println("in here 888");
                //dataInput.read(mybytearray,0,mybytearray.length);
                bufferedInputStream.read(mybytearray, 0, mybytearray.length); //read the file
                System.out.println("in here 4");


                outputStream.write(mybytearray, 0, mybytearray.length); //write file to the output stream byte by byte*/
                System.out.println("in here 6");
                outputStream.flush();
                bufferedInputStream.close();
                outputStream.close();
            }

            client.close();

            //text.setText("File Sent");


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



}
