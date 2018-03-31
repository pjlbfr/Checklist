package com.checklist.listtask;

import com.checklist.interfaces.BasePresenter;
import com.checklist.interfaces.BaseView;
import com.checklist.model.Task;

import java.util.List;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public interface ListTaskContract {
    interface View extends BaseView<Presenter>{
        void setData(List<Task> list);
    }

    interface Presenter extends BasePresenter{
        void initRealm();
        void getTasks();
        void removeTasks(List<Task> list);
    }
}
