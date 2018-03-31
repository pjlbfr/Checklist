package com.checklist.changetask;

import com.checklist.interfaces.BasePresenter;
import com.checklist.interfaces.BaseView;
import com.checklist.model.Task;

public interface ChangeTaskContract {
    interface View extends BaseView<ChangeTaskContract.Presenter> {
        void setData(Task task);
    }

    interface Presenter extends BasePresenter{
        void changeData(Task task);
        void getTask(String id);
    }
}
