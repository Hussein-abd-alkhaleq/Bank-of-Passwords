package com.example.bankofpasswords;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class Edit_Selected_Password_Dialog extends Dialog {

    public Edit_Selected_Password_Dialog(@NonNull Context context,List_View_Holder holder, String old_password_name,String old_password_value) {
        super(context);
        setContentView(R.layout.edit_selected_password_dialog);

        EditText display_password_name_edittext=findViewById(R.id.edit_password_name_edittext);
        EditText display_password_value_edittext=findViewById(R.id.edit_password_value_editText);

        display_password_name_edittext.setText(old_password_name);
        display_password_value_edittext.setText(old_password_value);

        Button update_selected_password= findViewById(R.id.update_selected_password_button);
        update_selected_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if (old_password_name.equals(display_password_name_edittext.getText().toString()) && old_password_name.equals(display_password_value_edittext.getText().toString())){
                       Toast.makeText(context, "not changed", Toast.LENGTH_SHORT).show();
                   }else {
                       String new_encrypted_password=new crypto().encrypt_with_AES(context,display_password_value_edittext.getText().toString());
                       new DataBase(context).update_password(old_password_name, display_password_name_edittext.getText().toString(),new_encrypted_password );
                       holder.password_name.setText(display_password_name_edittext.getText().toString());
                       holder.password_value.setText(display_password_value_edittext.getText().toString());
                       dismiss();
                   }
            }
        });

        Button cancel_update_selected_password=findViewById(R.id.cancel_update_selected_password_button);
        cancel_update_selected_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
