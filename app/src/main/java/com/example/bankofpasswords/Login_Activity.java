package com.example.bankofpasswords;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login_Activity extends AppCompatActivity {

    EditText password_edittext;
    Button next;
    Context local;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        local = this;

        password_edittext = findViewById(R.id.login_Password);
        next = findViewById(R.id.check_app_password);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        try {
                            SharedPreferences preferences = getSharedPreferences("login_password", Login_Activity.MODE_PRIVATE);
                            String stored_digest = preferences.getString("value", "");
                            String current_digest = new crypto().generate_SHA256_digest_string(password_edittext.getText().toString());

                            if (stored_digest.equals(current_digest)) {
                                Intent intent = new Intent(local, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                        Toast.makeText(local, "wrong login password", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            System.out.println(">>> login >>>>" + e.getMessage());
                        }
                    }


        });
    }
}