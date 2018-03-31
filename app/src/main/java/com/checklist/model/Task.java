package com.checklist.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public class Task extends RealmObject implements Parcelable{

    @PrimaryKey
    @Required
    private String id;
    private String description;
    private String date;

    public Task() {
    }

    public Task(String description, String date) {
        this.description = description;
        this.date = date;
    }

    public Task(String id, String description, String date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(description);
        parcel.writeString(date);
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int i) {
            return new Task[i];
        }
    };

    private Task(Parcel parcel) {
        id = parcel.readString();
        description = parcel.readString();
        date = parcel.readString();
    }
}
