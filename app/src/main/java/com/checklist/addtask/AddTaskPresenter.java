package com.checklist.addtask;

import android.content.Context;

import com.checklist.realm.RealmRequest;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public class AddTaskPresenter implements AddTaskContract.Presenter {

    private final AddTaskContract.View view;
    private Context context;
    private RealmRequest realmRequest;

    public AddTaskPresenter(Context context, AddTaskContract.View view) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
    }

    @Override
    public void initRealm() {
        realmRequest = new RealmRequest(context);
    }

    @Override
    public void insertData(String description) {
        realmRequest.insertTask(description);
    }

    @Override
    public void closeRealm() {
        realmRequest.close();
    }
}
