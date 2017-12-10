package com.whysoserious.neeraj.hospitalmanagementsystem.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by vladstarikov on 12/10/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (displayHomeButton()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean displayHomeButton() {
        return true;
    }
}
