package com.checklist.listtask;

import android.content.Context;

import com.checklist.realm.RealmRequest;
import com.checklist.model.Task;

import java.util.List;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public class ListTaskPresenter implements ListTaskContract.Presenter {

    private final ListTaskContract.View view;
    private Context context;
    private RealmRequest realmRequest;

    public ListTaskPresenter(Context context, ListTaskContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initRealm() {
        realmRequest = new RealmRequest(context);
    }

    @Override
    public void getTasks() {
        view.setData(realmRequest.readAllTasks());
    }

    @Override
    public void removeTasks(List<String> list) {
        realmRequest.removeTasks(list);
        getTasks();
    }

    @Override
    public void closeRealm() {
        realmRequest.close();
    }
}
