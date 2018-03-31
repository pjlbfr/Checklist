package com.checklist;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.checklist.addtask.AddTaskFragment;
import com.checklist.addtask.AddTaskPresenter;
import com.checklist.changetask.ChangeTaskFragment;
import com.checklist.changetask.ChangeTaskPresenter;
import com.checklist.listtask.ListTaskFragment;
import com.checklist.listtask.ListTaskPresenter;

public class MainActivity extends AppCompatActivity implements ListTaskFragment.OpenTaskListener{

   // private String LISTTASKFRAGMENT_TAG = "ListTaskFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("privet", "bundle = " + savedInstanceState);
        if (savedInstanceState == null) {
            ListTaskFragment listTaskFragment = ListTaskFragment.getInstance();
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.frameCont, listTaskFragment)
                                       .commit();
            new ListTaskPresenter(getApplicationContext(), listTaskFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            AddTaskFragment addTaskFragment = AddTaskFragment.getInstance();
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.frameCont, addTaskFragment)
                                       .addToBackStack(null)
                                       .commit();
            new AddTaskPresenter(getApplicationContext(), addTaskFragment);
        }

        if (item.getItemId() == android.R.id.home){
            backDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openTask(String id) {
        ChangeTaskFragment changeTaskFragment = ChangeTaskFragment.getInstance(id);
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.frameCont, changeTaskFragment)
                                   .addToBackStack(null)
                                   .commit();
        new ChangeTaskPresenter(getApplicationContext(), changeTaskFragment);
    }

    private void back(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            super.onBackPressed();
        else
            backDialog();
    }

    private void backDialog() {
        AlertDialog.Builder backDialog = new AlertDialog.Builder(
                MainActivity.this);
        backDialog.setTitle(getResources().getString(R.string.are_you_sure));

        backDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        back();
                    }
                });

        backDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        backDialog.show();
    }
}
