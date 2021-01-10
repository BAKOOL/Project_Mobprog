package com.example.ezyfoody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    protected static final String orderlist="order_list.txt" ;
    protected static final String historylist="history_list.txt";
    protected  static int wallet = 0;
    protected static int total_price=0;
    protected static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    public void setContentView(View view) {
        if (view.getId() == R.id.my_order) {
            check();
            Intent intent = new Intent(this, myOrder.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btn_drink){
            check();
            Intent intent = new Intent(this, drink.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btn_snack){
            check();
            Intent intent = new Intent(this, snack.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btn_food){
            check();
            Intent intent = new Intent(this, food.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btn_top_up){
            check();
            Intent intent = new Intent(this, topUp.class);
            startActivity(intent);
        }else if(view.getId()==R.id.history){
            check();
            Intent intent = new Intent(this, history.class);
            startActivity(intent);
        }
    }

    public  void check (){
        String isEmpty = "";

        FileInputStream fis = null;
        try {
            fis=openFileInput(orderlist);
            FileChannel cn = fis.getChannel();
            if (cn.size() !=0 && total_price==0  ) {
                FileOutputStream fos = null;
                fos = openFileOutput(orderlist, MODE_PRIVATE);
                fos.write(isEmpty.getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}