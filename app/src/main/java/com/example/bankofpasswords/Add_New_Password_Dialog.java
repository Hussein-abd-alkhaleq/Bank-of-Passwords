package com.example.bankofpasswords;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Add_New_Password_Dialog extends Dialog {

    public Add_New_Password_Dialog(@NonNull Context context, RecyclerView.Adapter adapter, List list) {

        super(context);
        setContentView(R.layout.add_new_password_dialog);

        Button add_new_password = findViewById(R.id.add_new_password_button);
        add_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name_edittext = findViewById(R.id.new_password_name_editText);
                EditText password1 = findViewById(R.id.new_passwoed_value_edittext_1);
                EditText password2 = findViewById(R.id.new_passwoed_value_edittext_2);

                if (!name_edittext.getText().toString().equals("") && !password1.getText().toString().equals("") && !password2.getText().toString().equals("")) {
                    if (password1.getText().toString().equals(password2.getText().toString())) {
                        String password = password1.getText().toString();
                        String name = name_edittext.getText().toString();

                        DataBase dataBase = new DataBase(context);
                        if (!dataBase.same_name_exist(name)) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        dataBase.insert(name, new crypto().encrypt_with_AES(context,password));
                                }
                            }).start();

                            list.add(new List_Item(name,password));
                            adapter.notifyDataSetChanged();
                            dismiss();
                        }else Toast.makeText(getContext(), "the name of password is exist", Toast.LENGTH_SHORT).show();

                    } else Toast.makeText(getContext(), "password values are not identical", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(getContext(), "enter all values", Toast.LENGTH_SHORT).show();
            }
        });

        Button cancel_add_new_password = findViewById(R.id.cancel_add_new_password_button);
        cancel_add_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
