package com.whysoserious.neeraj.hospitalmanagementsystem.Patient.View_Report;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.whysoserious.neeraj.hospitalmanagementsystem.R;
import com.whysoserious.neeraj.hospitalmanagementsystem.activity.BaseActivity;

/**
 * Created by Neeraj on 08-Apr-16.
 */
public class Final_View_Report extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_view_report);

        Bundle bb = getIntent().getExtras();
        String report = bb.getString("report");
        TextView final_report = findViewById(R.id.tv_report);
        final_report.setText(report);
    }
}