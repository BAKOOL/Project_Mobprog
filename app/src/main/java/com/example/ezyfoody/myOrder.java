package com.example.ezyfoody;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class myOrder extends MainActivity {

    String afterbuy_cencel="", beforeCreateHistory;

    TextView address, ordered_all, Store, Transaction, yourmoney, bill;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder);

        ordered_all = findViewById(R.id.textView);
        Transaction = findViewById(R.id.clear_order);

        registerForContextMenu(Transaction);

        address= findViewById(R.id.address);
        Store=findViewById(R.id.store);


        yourmoney = findViewById(R.id.money);
        yourmoney.setText(String.valueOf(wallet));

        bill = findViewById(R.id.total_you_must_pay);
        bill.setText(String.valueOf(total_price));

        Intent intent = getIntent();
        address.setText(intent.getStringExtra("address"));
        Store.setText(intent.getStringExtra("store"));

        FileInputStream fis=null;

        try {
            fis=openFileInput(orderlist);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }
                ordered_all.setText(sb.toString());

            // bug= kalau program ditutup tapi orderlist != null, maka saat aplikasi dijalankan lg, orderlist nya tidak kosong namun total_price=0;
            // menurut saya solusinya setiap kali exit program orderlistnya dikosongkan, tapi di program ini gak ada exitnya hehe
            //gk jd. ud sovled hehe. saya menambahkan memanggil method check() untuk mengatasi bugnya....
            //alternatif internal storage di ganti menjadi arraylist

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

    public void my_orderlyout(View view) {
        if(view.getId()== R.id.my_order_back){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(view.getId()==R.id.my_order_location){

            Intent intent = new Intent(this, location.class);
            startActivity(intent);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.my_order_popup, menu);

    }

    public void menu_of_myorder(MenuItem item) {

        if(item.getItemId() == R.id.Buy){

            if(address.getText().toString().equals("")){
                Toast.makeText(this, "insert your address", Toast.LENGTH_SHORT).show();
            }else if(Store.getText().toString().equals("")){
                Toast.makeText(this, "select store", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(bill.toString()) > Integer.parseInt(yourmoney.toString())){
                Toast.makeText(this, "not enough money", Toast.LENGTH_SHORT).show();
            }else{
                //order dimasukkan kedalam history
                craeteHistory();

                //hapus semua orderlist(terbeli)
                FileOutputStream fos = null;
                try {
                    fos= openFileOutput(orderlist,MODE_PRIVATE);
                    fos.write(afterbuy_cencel.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (fos != null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                wallet= wallet - total_price;
                total_price=0;

                count= count+1;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }else  if(item.getItemId() == R.id.delete){

            //hapus
            FileOutputStream fos = null;
            try {
                fos= openFileOutput(orderlist,MODE_PRIVATE);
                fos.write(afterbuy_cencel.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            total_price=0;

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void craeteHistory() {
        String all_history;
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        FileInputStream fis = null;
        try {
            fis= openFileInput(historylist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel cn = fis.getChannel();
        try {
            if (cn.size()!= 0 && count == 0){
                in();
                all_history= beforeCreateHistory + ordered_all.getText().toString() + "\n" +
                        address.getText().toString() + "\n" +
                        Store.getText().toString() +"\n"+
                        date + "\n"
                        + "-------------------------------------------------------\n";
                out(all_history);
            } else if(count == 0){
                all_history= ordered_all.getText().toString() + "\n" +
                        "-------------------------------------------------------\n";
                out(all_history);
            }else if (count > 0){
            in();
            all_history= beforeCreateHistory + ordered_all.getText().toString() + "\n" +
                    "-------------------------------------------------------\n";
            out(all_history);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void in(){
        FileInputStream fis = null;

        try {
            fis= openFileInput(historylist);
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader br= new BufferedReader(isr);
            StringBuilder sb= new StringBuilder();

            String text;

            while ((text= br.readLine()) != null){
                sb.append(text).append("\n");
            }

            beforeCreateHistory= sb.toString();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if( fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void out(String word){
        FileOutputStream fos = null;
        try {
            fos= openFileOutput(historylist, MODE_PRIVATE);
            fos.write(word.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
