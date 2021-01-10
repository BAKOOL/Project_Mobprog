package com.example.ezyfoody;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class topUp extends MainActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup);
    }

    public void add_money(View view) {
        if (view.getId() == R.id.topup_ops1){
            wallet += 50000;
            Toast toast = Toast.makeText(this, "add 50000 to your wallet",Toast.LENGTH_SHORT);
            toast.show();
        } else if (view.getId() == R.id.topup_ops2){
            wallet += 100000;
            Toast toast = Toast.makeText(this, "add 100000 to your wallet",Toast.LENGTH_SHORT);
            toast.show();
        }else if (view.getId() == R.id.topup_ops3){
            wallet += 500000;
            Toast toast = Toast.makeText(this, "add 500000 to your wallet",Toast.LENGTH_SHORT);
            toast.show();
        }else if (view.getId() == R.id.topup_ops4){
            wallet += 2000000;
            Toast toast = Toast.makeText(this, "add 1000000 to your wallet",Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
