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

public class order extends drink  {

    TextView selected_drink;
    String order_beforesubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        selected_drink= findViewById(R.id.this_drink);
        FileInputStream fis=null;

        try {
            fis = openFileInput(drink_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text ;

            while ((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            selected_drink.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

        String drink_submited;
        EditText Quantity= findViewById(R.id.Drink_quantity);

        if (view.getId() == R.id.my_order){
            Intent intent = new Intent(this, myOrder.class);
            startActivity(intent);
        }
        else  if (view.getId() == R.id.submit){



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
            }

            drink_submited= order_beforesubmit + selected_drink.getText().toString() + " " + Quantity.getText().toString() + "\n" ;

            FileOutputStream fos=null;
            try {
                fos=openFileOutput(orderlist,MODE_PRIVATE);
                fos.write(drink_submited.getBytes() );
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
                    }finally {
                        if (fos!= null){
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            String tes = Quantity.getText().toString();
            total_price = total_price + (price* Integer.parseInt(tes)) ;

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else  if (view.getId() == R.id.drink_back){
            Intent intent = new Intent(this, drink.class);
            startActivity(intent);
        }
    }
}
