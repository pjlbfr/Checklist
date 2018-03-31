package com.checklist.addtask;

import com.checklist.interfaces.BasePresenter;
import com.checklist.interfaces.BaseView;
import com.checklist.model.Task;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public interface AddTaskContract {
    interface View extends BaseView<Presenter>{
    }

    interface Presenter extends BasePresenter{
        void insertData(Task task);
    }
}
