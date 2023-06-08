package com.example.bankofpasswords;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class List_Adapter extends RecyclerView.Adapter<List_View_Holder> {

    Context context;
    List<List_Item> list;

    public List_Adapter(Context context, List<List_Item> list_) {
        this.context = context;
        this.list = list_;
    }

    @NonNull
    @Override
    public List_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new List_View_Holder(LayoutInflater.from(context).inflate(R.layout.recyclerview_row_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull List_View_Holder holder, @SuppressLint("RecyclerView") int position) {

        holder.password_name.setText(list.get(position).password_name);
        holder.password_value.setText(list.get(position).password_value);
        /////////////////////////////////////////////////////////////////
        holder.delete_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(holder.delete_password.getContext()).setTitle("Delete Password").setMessage("delete this password: " + holder.password_name.getText().toString() + " ??").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                        new DataBase(holder.password_name.getContext()).delete_password(holder.password_name.getText().toString());
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        //////////////////////////////////////////////////////////////////
        holder.copy_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "password has been copied to clipboard", Toast.LENGTH_LONG).show();
                ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", holder.password_value.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });
        /////////////////////////////////////////////////////////////////
        holder.edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Edit_Selected_Password_Dialog(holder.password_name.getContext(),holder, holder.password_name.getText().toString(), holder.password_value.getText().toString()).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<List_Item> list_){
        list = list_;
        notifyDataSetChanged();
    }
}
