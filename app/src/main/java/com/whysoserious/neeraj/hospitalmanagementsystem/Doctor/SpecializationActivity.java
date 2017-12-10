package com.whysoserious.neeraj.hospitalmanagementsystem.Doctor;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;
import com.whysoserious.neeraj.hospitalmanagementsystem.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Neeraj on 02-Apr-16.
 */
public class SpecializationActivity extends BaseActivity {

    ListView lvs;
    String s, username, password, user_type;
    EditText et;

    final List<String> sp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialization);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lvs = findViewById(R.id.lv_specialization);
        lvs.setOnItemClickListener((parent, view, position, id) -> {
            sp.remove(position);
            try (DatabaseHelper db = new DatabaseHelper(SpecializationActivity.this);
                 Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_slot))) {
                if (y.moveToFirst()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (!sp.isEmpty()) {
                        for (String s : sp) {
                            stringBuilder.append(s);
                            stringBuilder.append('_');
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    }

                    boolean b = db.update_slot(username, password, stringBuilder.toString(), y.getString(3), y.getString(4), y.getString(5));
                    Message.message(SpecializationActivity.this, b ? "Your SpecializationActivity Has been Updated" : "Some Error Occured, Try Again");

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sp);
                    lvs.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        et = findViewById(R.id.et_spl);

        sp.clear();

        try (
                DatabaseHelper dbh = new DatabaseHelper(this);
                Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_slot))
        ) {
            if (y.moveToFirst()) {
                String br = y.getString(2);
                sp.addAll(
                        Arrays.stream(br.split("_"))
                                .filter(s1 -> !s1.isEmpty())
                                .collect(Collectors.toList())
                );
                if (sp.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sp);
                    lvs.setAdapter(adapter);
                } else {
                    Message.message(SpecializationActivity.this, "Sorry, You have No SpecializationActivity");
                }
            } else {
                Message.message(SpecializationActivity.this, "Sorry, You have No SpecializationActivity");
            }
        }
    }

    public void onClick(View view) {
        s = et.getText().toString();
        if (s.length() > 0) {
            try (
                    DatabaseHelper db = new DatabaseHelper(this);
                    Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_slot));
            ) {
                if (y.moveToFirst()) {
                    boolean b = db.update_slot(username, password, y.getString(2) + "_" + s, y.getString(3), y.getString(4), y.getString(5));
                    Message.message(SpecializationActivity.this, b ? "Your SpecializationActivity Has been Updated" : "Some Error Occured, Try Again");
                } else {
                    boolean b = db.insert_slot(username, password, "_" + s, "-", "-", "Y");
                    Message.message(SpecializationActivity.this, b ? "Your SpecializationActivity Has been Inserted" : "Some Error Occured, Try Again");
                }

                sp.add(s);
                ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sp);
                lvs.setAdapter(adapter);
                et.getText().clear();
            }
        } else {
            Message.message(SpecializationActivity.this, "Please Write Your SpecializationActivity");
        }
    }
}
