package com.example.bankofpasswords;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.annotation.Nullable;


public class Launch_Activity extends Activity {
Context local;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local=this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("login_password", Launch_Activity.MODE_PRIVATE);
                String x = preferences.getString("value", "");

                if (x != "") {
                    Intent intent = new Intent(local, Login_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(local, Login_Password_Assigning_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();

    }
}
