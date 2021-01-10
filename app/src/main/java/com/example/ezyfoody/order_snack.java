package com.example.ezyfoody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class order_snack extends snack {

    TextView selected_snack;
    String order_beforesubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_snack);

        selected_snack = findViewById(R.id.this_snack);

        FileInputStream fis =null;

        try {
            fis= openFileInput(Snack_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text;

            while ((text= br.readLine()) != null){
                sb.append(text).append("\n");
            }

            selected_snack.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setContentView_onorder(View view) {

        String snack_submited;
        EditText Quantity= findViewById(R.id.snack_quantity);

        if (view.getId() == R.id.my_order){
            Intent intent = new Intent(this, myOrder.class);
            startActivity(intent);
        } else if (view.getId() == R.id.submit){

            FileInputStream fis = null;
            try {
                fis=openFileInput(orderlist);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();

                String text;

                while((text= br.readLine()) != null){
                    sb.append(text).append("\n");
                }
                order_beforesubmit= sb.toString();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            snack_submited= order_beforesubmit + selected_snack.getText().toString() + " " + Quantity.getText().toString() + "\n" ;

            FileOutputStream fos=null;
            try {
                fos=openFileOutput(orderlist,MODE_PRIVATE);
                fos.write(snack_submited.getBytes() );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (fos!= null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            String tes = Quantity.getText().toString();

            total_price = total_price+ (price* Integer.parseInt(tes)) ;

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (view.getId() == R.id.snack_back){
            Intent intent = new Intent(this, snack.class);
            startActivity(intent);
        }
    }
}
