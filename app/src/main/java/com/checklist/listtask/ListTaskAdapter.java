package com.checklist.listtask;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.checklist.R;
import com.checklist.interfaces.ActionModeItemListener;
import com.checklist.interfaces.OnTaskSelectedListener;
import com.checklist.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ViewHolder>{

    private List<Task> tasks = new ArrayList<>();
    private OnTaskSelectedListener onTaskSelectedListener;
    private ActionModeItemListener actionModeItemListener;
    private boolean isActionMode = false;
    private ActionMode actionMode;
    private List<String> selectPosition = new ArrayList<>();

    public ListTaskAdapter(OnTaskSelectedListener onTaskSelectedListener, ActionModeItemListener actionModeItemListener){
        this.onTaskSelectedListener = onTaskSelectedListener;
        this.actionModeItemListener = actionModeItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Task task = tasks.get(position);
        final CardView cardView = holder.cardView;
        final String id = task.getId();
        if (selectPosition.contains(id))
            cardView.setCardBackgroundColor(Color.GRAY);
        else
            cardView.setCardBackgroundColor(Color.WHITE);
        holder.description.setText(task.getDescription());
        holder.date.setText(task.getDate());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isActionMode){
                    if (selectPosition.contains(id)){
                        unselectItem(cardView, id);
                    } else {
                        selectItem(cardView, id);
                    }
                } else
                    if (onTaskSelectedListener != null)
                        onTaskSelectedListener.onTaskSelected(id); //go on update task
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (actionMode == null) {
                    actionMode = ((AppCompatActivity) view.getContext()).startActionMode(actionModeCallback);
                    selectItem(cardView, task.getId());
                    return true;
                }
                return false;
            }
        });
    }

    private void selectItem(CardView cardView, String id){
        selectPosition.add(id);
        cardView.setCardBackgroundColor(Color.GRAY);

        actionMode.setTitle(String.valueOf(selectPosition.size()));
    }

    private void unselectItem(CardView cardView, String id){
        selectPosition.remove(id);
        cardView.setCardBackgroundColor(Color.WHITE);
        actionMode.setTitle(String.valueOf(selectPosition.size()));
    }

    public void setData(List<Task> list){
        tasks.clear();
        tasks.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView description;
        private TextView date;
        private CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            description = (TextView) cardView.findViewById(R.id.text_description);
            date = (TextView) cardView.findViewById(R.id.text_date);
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            isActionMode = true;
            mode.getMenuInflater().inflate(R.menu.menu_list_fragment, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (actionModeItemListener != null){
                actionModeItemListener.onDeletePressed(selectPosition);
                mode.finish();
            }
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            selectPosition.clear();
            actionMode = null;
            notifyDataSetChanged();
        }
    };
}
