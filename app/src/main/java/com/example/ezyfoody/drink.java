package com.example.ezyfoody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class drink extends MainActivity implements PopupMenu.OnMenuItemClickListener {

    public static final String drink_file ="drink.txt";
    TextView air;
    TextView apel;
    TextView soda;
    TextView anggur;
    TextView price_air;
    TextView price_apel;
    TextView price_soda;
    TextView price_anggur;
    protected  static int price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink);

       air=findViewById(R.id.air_mineral);
       apel=findViewById(R.id.jus_ap);
       soda=findViewById(R.id.soda);
       anggur=findViewById(R.id.juz_ang);
       price_air=findViewById(R.id.harga_airmineral);
       price_apel=findViewById(R.id.harga_apel);
       price_soda=findViewById(R.id.harga_soda);
       price_anggur=findViewById(R.id.harga_anggur);
    }

    public void orderDrink(View view) {
        FileOutputStream fos = null;
        String text = "";

        price=0;

        if(view.getId() == R.id.air_mineral){
            price+=3000;
            text = air.getText().toString() + "\n" + price_air.getText().toString();
        }else if(view.getId() == R.id.jus_ap){
            price+=5000;
            text = apel.getText().toString() + "\n" + price_apel.getText().toString();
        }else if(view.getId() == R.id.soda){
            price+=27000;
            text = soda.getText().toString() + "\n" + price_soda.getText().toString();
        }else if(view.getId() == R.id.juz_ang){
            price+=30000;
            text = anggur.getText().toString() + "\n" + price_anggur.getText().toString();
        }


        try {
            fos = openFileOutput(drink_file,MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.drink_popup);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void odrder_popup(MenuItem item) {
        Intent intent = new Intent(this, order.class);
        startActivity(intent);
    }
}
