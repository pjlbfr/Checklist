package com.checklist.realm;

import android.content.Context;

import com.checklist.model.Task;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by Zodiakaio on 25.03.2018.
 *
 */

public class RealmRequest {

    private Realm realm;

    public RealmRequest(Context context){
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public Task getTask(String id){
        return realm.where(Task.class).equalTo("id", id).findFirst();
    }

    public void insertTask(final Task task){
        realm.beginTransaction();
        Task element = realm.createObject(Task.class, UUID.randomUUID().toString());
        element.setDescription(task.getDescription().trim());
        element.setDate(task.getDate());
        realm.commitTransaction();
    }

    public void changeTask(Task task){
        realm.beginTransaction();
        Task element = realm.where(Task.class).equalTo("id", task.getId()).findFirst();
        element.setDescription(task.getDescription());
        realm.commitTransaction();
    }

    public void removeTasks(List<Task> list){
        realm.beginTransaction();
        for (int i = 0; i < list.size(); i++){
            Task task = realm.where(Task.class)
                             .equalTo("id", list.get(i).getId())
                             .findFirst();
            if (task != null)
                task.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public List<Task> readAllTasks(){
        return realm.where(Task.class).findAll();
    }

    public void close(){
        realm.close();
    }
}
