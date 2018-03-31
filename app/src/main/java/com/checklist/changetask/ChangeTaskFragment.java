package com.checklist.changetask;

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

public class ChangeTaskFragment extends Fragment implements ChangeTaskContract.View{

    public static final String CHANGE_TAG = "Change_Tag";
    private ChangeTaskContract.Presenter presenter;
    private EditText editText;
    private String id;
    private Task task;

    public static ChangeTaskFragment getInstance(String id){
        ChangeTaskFragment changeTaskFragment = new ChangeTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHANGE_TAG, id);
        changeTaskFragment.setArguments(bundle);
        return changeTaskFragment;
    }

    @Override
    public void setPresenter(ChangeTaskContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if (getArguments() != null)
            id = getArguments().getString(CHANGE_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task, null);
        if (getActivity() != null) {
            getActivity().setTitle(getResources().getString(R.string.change_title));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        editText = (EditText) view.findViewById(R.id.editText);
        presenter.initRealm();
        presenter.getTask(id);
        return view;
    }

    @Override
    public void setData(Task element) {
        task = element;
        editText.setText(task.getDescription());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_change, menu);
        menu.findItem(R.id.action_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change){
            String textInEditText = editText.getText().toString().trim();
            if (textInEditText.isEmpty() && getView() != null){
                Snackbar.make(getView(), getResources().getString(R.string.not_be_empty), Snackbar.LENGTH_LONG).show();
            }
            else if(textInEditText.equals(task.getDescription()) && getView() != null){
                Snackbar.make(getView(), getResources().getString(R.string.change_task), Snackbar.LENGTH_LONG).show();
            }
            else {
                Task element = new Task(task.getId(), textInEditText, task.getDate());
                presenter.changeData(element);
                close();
            }
        }
        return super.onOptionsItemSelected(item);
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
