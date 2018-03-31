package com.checklist.listtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.checklist.R;
import com.checklist.interfaces.OnTaskSelectedListener;
import com.checklist.model.Task;

import java.util.List;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public class ListTaskFragment extends Fragment implements ListTaskContract.View{

    private ListTaskContract.Presenter presenter;
    private ListTaskAdapter listTaskAdapter;

    public interface OpenTaskListener {
        void openTask(String id);
    }

    public static ListTaskFragment getInstance(){
        return new ListTaskFragment();
    }

    @Override
    public void setPresenter(ListTaskContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view, null);
        if (getActivity() != null) {
            getActivity().setTitle(R.string.list_task_title);
          //  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listTaskAdapter = new ListTaskAdapter(onTaskSelectedListener);
        recyclerView.setAdapter(listTaskAdapter);
        presenter.initRealm();
        presenter.getTasks();
        return view;
    }

    @Override
    public void setData(List<Task> list) {
        listTaskAdapter.setData(list);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete){
            presenter.removeTasks(listTaskAdapter.getRemoveTasks());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.closeRealm();
    }

    OnTaskSelectedListener onTaskSelectedListener = new OnTaskSelectedListener() {
        @Override
        public void onTaskSelected(String id) {
            if (getActivity() != null)
                ((OpenTaskListener)getActivity()).openTask(id);
        }
    };
}
