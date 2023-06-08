package com.example.bankofpasswords;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class List_View_Holder extends RecyclerView.ViewHolder {
    TextView password_name;
    TextView password_value;
    ImageView copy_password;
    ImageView edit_password;
    ImageView delete_password;

    public List_View_Holder(@NonNull View itemView) {
        super(itemView);
        password_name = itemView.findViewById(R.id.password_name_textview);
        password_value = itemView.findViewById(R.id.password_value_textview);

        copy_password = itemView.findViewById(R.id.copy_password);
        edit_password = itemView.findViewById(R.id.edit_password);
        delete_password = itemView.findViewById(R.id.delete_password);
    }
}
