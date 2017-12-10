package com.whysoserious.neeraj.hospitalmanagementsystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Desktop_Admin.Desktop_Admin;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Doctor;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.Patient.Patient;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;
import com.whysoserious.neeraj.hospitalmanagementsystem.Staff_Member.Staff_Member;

/**
 * Created by Neeraj on 17-Mar-16.
 */
public class LoginActivity extends BaseActivity {

    EditText username, password;
    String usernames, passwords;
    Button Bregister, Blogin;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.etusername);
        password = findViewById(R.id.etpassword);
        Bregister = findViewById(R.id.bregister);
        Blogin = findViewById(R.id.blogin);
        dbh = new DatabaseHelper(this);

        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = username.getText().toString();
                passwords = password.getText().toString();

                Cursor y = dbh.checkduplicates_in_user_credentials(usernames, passwords, getResources().getString(R.string.user_credentials));

                if (y.moveToFirst()) {
                    String ut = y.getString(7);
                    Message.message(LoginActivity.this, "Welcome");

                    Bundle b = new Bundle();
                    b.putString("username", usernames);
                    b.putString("password", passwords);
                    b.putString("user_type", ut);

                    Intent i;
                    if (ut.equals("Doctor")) {
                        i = new Intent(LoginActivity.this, Doctor.class);
                    } else if (ut.equals("Patient")) {
                        i = new Intent(LoginActivity.this, Patient.class);
                    } else if (ut.equals("Staff Member")) {
                        i = new Intent(LoginActivity.this, Staff_Member.class);
                    } else {
                        i = new Intent(LoginActivity.this, Desktop_Admin.class);
                    }

                    i.putExtras(b);
                    startActivity(i);
                } else {
                    Message.message(LoginActivity.this, "No Such User Exists");
                    Message.message(LoginActivity.this, "Please RegisterActivity Your self");
                }

                y.close();
            }
        });
        dbh.close();
    }
}
