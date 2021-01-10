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
import java.io.Writer;


public class food extends MainActivity implements PopupMenu.OnMenuItemClickListener {

    public final String food_file ="food.txt";
    TextView burger;
    TextView Jkebab;
    TextView kentang;
    TextView nasi;
    TextView price_burger;
    TextView price_jkebab;
    TextView price_kentang;
    TextView price_nasi;
    static protected int price;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food);

        burger=findViewById(R.id.buger);
        Jkebab=findViewById(R.id.kebab);
        kentang=findViewById(R.id.kentang);
        nasi=findViewById(R.id.nasi);
        price_burger=findViewById(R.id.harga_buger);
        price_jkebab=findViewById(R.id.harga_jkebab);
        price_kentang=findViewById(R.id.harga_kentang);
        price_nasi=findViewById(R.id.harga_nasi);

    }

    public void orderFood(View view) {

        String text ="";
        FileOutputStream fos =null;

        price=0;

        if (view.getId() == R.id.buger){
            price+=25000;
            text= burger.getText().toString() + "\n" + price_burger.getText().toString();
        }else if (view.getId() == R.id.kebab){
            price+=15000;
            text= Jkebab.getText().toString() + "\n" + price_jkebab.getText().toString();
        }else if (view.getId() == R.id.kentang){
            price+=10000;
            text= kentang.getText().toString() + "\n" + price_kentang.getText().toString();
        }else if (view.getId() == R.id.nasi){
            price+=50000;
            text= nasi.getText().toString() + "\n" + price_nasi.getText().toString();
        }


        try {
            fos=openFileOutput(food_file,MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.food_popup);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void food_order_popup(MenuItem item) {
        Intent intent = new Intent(this, order_food.class);
        startActivity(intent);
    }
}
