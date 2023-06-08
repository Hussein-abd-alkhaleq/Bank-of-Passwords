package com.example.bankofpasswords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List_Adapter adapter;
    Change_Login_Password_Dialog dialog;
    List<List_Item> stored_passwords_list;
    Activity local = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton setting = (ImageButton) findViewById(R.id.setting_button);
        PopupMenu menu = new PopupMenu(MainActivity.this, setting);
        menu.inflate(R.menu.setting_menu);
        //////////////////////////////////////
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Change Login Password")) {
                    dialog = new Change_Login_Password_Dialog(local);
                    dialog.show();
                } else if (menuItem.getTitle().equals("Reset")) { //////////////// reset
                    AlertDialog.Builder builder = new AlertDialog.Builder(local);
                    builder.setTitle("Reset the application");
                    builder.setMessage("All data will be deleted, confirm Reset");
                    builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences preferences1 = getSharedPreferences("login_password", Login_Password_Assigning_Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences1.edit();
                            editor.putString("value",null);
                            editor.apply();
                            new DataBase(local).reset();
                            Intent intent=new Intent(local,Launch_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Dialog x=builder.create();
                            x.dismiss();
                        }
                    });
                    builder.show();

                } else if (menuItem.getTitle().equals("Exit")) {
                    MainActivity.this.finish();
                }
                return false;
            }
        });
        ////////////////////////////////////
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });
        ///////////////////////////////////
        recyclerView = findViewById(R.id.app_recyclar_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(local));
        stored_passwords_list = new DataBase(local).get_all_stored_passwords();

        adapter = new List_Adapter(local, stored_passwords_list);
        recyclerView.setAdapter(adapter);

        ////////////////////////////////////
        FloatingActionButton add_new_password = findViewById(R.id.add_new_password);
        add_new_password.setOnClickListener(view -> {
            Add_New_Password_Dialog dialog = new Add_New_Password_Dialog(local, adapter, stored_passwords_list);
            dialog.show();
        });
        ////////////////////////////////////
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                List<List_Item> temp = new ArrayList();
                for (List_Item d : stored_passwords_list) {
                    if (d.password_name.contains(s)) {
                        temp.add(d);
                    }
                }
                adapter.updateList(temp);
                return true;
            }
        });
    }
}