package com.checklist.addtask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.checklist.R;
import com.checklist.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public class AddTaskFragment extends Fragment implements AddTaskContract.View{

    private AddTaskContract.Presenter presenter;
    private EditText editText;

    public static AddTaskFragment getInstance(){
        return new AddTaskFragment();
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task, null);
        presenter.initRealm();
        if (getActivity() != null){
            getActivity().setTitle(R.string.add_title);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        editText = (EditText) view.findViewById(R.id.editText);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
        menu.findItem(R.id.action_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_task){
            if (!editText.getText().toString().trim().isEmpty()) {
                Task task = new Task(editText.getText().toString().trim(), getDate());
                presenter.insertData(task);
                close();
            } else if (getView() != null){
                Snackbar.make(getView(), getResources().getString(R.string.not_be_empty), Snackbar.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        return format.format(new Date());
    }

    private void close(){
        if (getActivity() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.closeRealm();
    }
}