package com.example.bankofpasswords;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Password_Assigning_Activity extends AppCompatActivity {
    Activity local;
    EditText password;
    EditText confirm_password;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password_assigning);
        local = this;


        password = findViewById(R.id.Login_Password1);
        confirm_password = findViewById(R.id.Login_Password2);
        next = findViewById(R.id.store_app_password);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals(confirm_password.getText().toString())) {
                    if (password.getText().length() >= 8) {

                        try {
                            SharedPreferences preferences1 = getSharedPreferences("login_password", Login_Password_Assigning_Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences1.edit();
                            String password_digest=new crypto().generate_SHA256_digest_string(password.getText().toString());
                            editor.putString("value",password_digest);
                            editor.apply();
                            System.out.println(password_digest);////////////>>>>

                        } catch (Exception e) {
                            Toast.makeText(local, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataBase helper = new DataBase(getApplicationContext());
                                helper.close();
                            }
                        }).start();

                        Intent intent = new Intent(local, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(local, "the password length must be at minimum 8 characters", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(local, "You entered different value", Toast.LENGTH_LONG).show();

            }
        });

    }


}