package com.example.bankofpasswords;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Change_Login_Password_Dialog extends Dialog {

    public Change_Login_Password_Dialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.change_login_password_dialog);

        Button change_login_password= findViewById(R.id.change_login_password_button);
        change_login_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText old_login_password = findViewById(R.id.old_login_password_edittext);
                EditText new_login_password = findViewById(R.id.confrim_new_login_password_edittext);
                EditText confirm_new_login_password = findViewById(R.id.confrim_new_login_password_edittext);

                String old_password = old_login_password.getText().toString();
                String new_password = new_login_password.getText().toString();
                String confirm_password = confirm_new_login_password.getText().toString();

                SharedPreferences preferences = context.getSharedPreferences("login_password", 0);
                String stored_login_password = preferences.getString("value", null);

                if (stored_login_password.equals(new crypto().generate_SHA256_digest_string(old_password))) {
                    if (new_password.equals(confirm_password) && new_password.length()>=8){

                        String new_digest=new crypto().generate_SHA256_digest_string(new_password);

                        List<List_Item> list=new ArrayList<>();
                        list=new DataBase(context).get_all_stored_passwords();

                        for (int i=0;i<list.size();i++){
                            new DataBase(context).update_password(list.get(i).password_name,list.get(i).password_name,new crypto().encrypt_with_AES(list.get(i).password_value,new_digest));
                        }

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("value",new_digest);
                        editor.commit();
                        dismiss();

                    }else Toast.makeText(context, "new password and confirm fields are not the same, or new password length is not enough", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "the old password is wrong", Toast.LENGTH_LONG).show();

            }
        });

        Button cancel_change_login_password=findViewById(R.id.cancel_change_login_password);
        cancel_change_login_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
