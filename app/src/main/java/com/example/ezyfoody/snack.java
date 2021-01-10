package com.example.ezyfoody;

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

public class snack extends MainActivity implements PopupMenu.OnMenuItemClickListener {

    public final String Snack_file="snack.txt";
    TextView permen ;
    TextView gula ;
    TextView bawanggoreng ;
    TextView karet ;
    TextView price_permen ;
    TextView price_gula ;
    TextView price_bawanggoreng ;
    TextView price_karet ;
    protected static int price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack);

        permen= findViewById(R.id.permen);
        gula= findViewById(R.id.momogi);
        bawanggoreng= findViewById(R.id.twist);
        karet= findViewById(R.id.fitbar);
        price_permen= findViewById(R.id.harga_twist);
        price_gula= findViewById(R.id.harga_momogi);
        price_bawanggoreng= findViewById(R.id.harga_permen);
        price_karet= findViewById(R.id.harga_Fitbar);

    }


    public void snack_popup(View view) {

        price=0;

        String text ="";
        FileOutputStream fos = null;
        if (view.getId() == R.id.permen){
            price+=100000;
            text=permen.getText().toString() + "\n" + price_permen.getText().toString();
        }else if (view.getId() == R.id.momogi){
            price+=15000;
            text=gula.getText().toString() + "\n" + price_gula.getText().toString();
        }else if (view.getId() == R.id.twist){
            price+=73000;
            text=bawanggoreng.getText().toString() + "\n" + price_bawanggoreng.getText().toString();
        }else if (view.getId() == R.id.fitbar){
            price+=4000;
            text=karet.getText().toString() + "\n" + price_karet.getText().toString();
        }

        try {
            fos= openFileOutput(Snack_file,MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.snack_popup);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void snack_order_popup(MenuItem item) {
        Intent intent = new Intent(this, order_snack.class);
        startActivity(intent);
    }
}
