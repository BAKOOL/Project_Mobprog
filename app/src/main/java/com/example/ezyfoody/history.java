package com.example.ezyfoody;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

public class history extends MainActivity {

    TextView myHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myHistory= findViewById(R.id.my_history);

        File file = getFileStreamPath("history_list.txt");
        if(file.exists()){
        fileSystem();
        }else {
            myHistory.setText("no History");
        }

;

    }

    private void fileSystem(){
        FileInputStream fis = null;
        try {
            fis= openFileInput(historylist);

            FileChannel cn = fis.getChannel();
            if (cn.size() == 0){
                myHistory.setText("no history...");
            }else{
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();

                String text;

                while((text= br.readLine()) != null){
                    sb.append(text).append("\n");
                }

                myHistory.setText(sb.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}