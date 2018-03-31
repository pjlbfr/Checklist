package com.checklist.changetask;

import android.content.Context;

import com.checklist.model.Task;
import com.checklist.realm.RealmRequest;

public class ChangeTaskPresenter implements ChangeTaskContract.Presenter{

    private final ChangeTaskContract.View view;
    private Context context;
    private RealmRequest realmRequest;

    public ChangeTaskPresenter(Context context, ChangeTaskContract.View view) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
    }

    @Override
    public void initRealm() {
        realmRequest = new RealmRequest(context);
    }

    @Override
    public void changeData(Task task) {
        realmRequest.changeTask(task);
    }

    @Override
    public void getTask(String id) {
        view.setData(realmRequest.getTask(id));
    }

    @Override
    public void closeRealm() {
        realmRequest.close();
    }
}
