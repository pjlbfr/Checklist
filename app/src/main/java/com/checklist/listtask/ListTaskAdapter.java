package com.checklist.listtask;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.checklist.R;
import com.checklist.interfaces.OnTaskSelectedListener;
import com.checklist.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Zodiakaio on 24.03.2018.
 *
 */

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ViewHolder>{

    private List<Task> tasks = new ArrayList<>();
    private List<Boolean> checks = new ArrayList<>();
    private OnTaskSelectedListener onTaskSelectedListener;

    public ListTaskAdapter(OnTaskSelectedListener onTaskSelectedListener){
        this.onTaskSelectedListener = onTaskSelectedListener;
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
        holder.description.setText(task.getDescription());
        holder.date.setText(task.getDate());
        holder.checkBox.setChecked(checks.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checks.set(position, !checks.get(position));
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTaskSelectedListener != null)
                  onTaskSelectedListener.onTaskSelected(task.getId());
            }
        });
    }

    public void setData(List<Task> list){
        tasks.clear();
        tasks.addAll(list);
        checks.clear();
        checks.addAll(new ArrayList<Boolean>(Collections.nCopies(tasks.size(), false)));
        notifyDataSetChanged();
    }

    public List<Task> getRemoveTasks(){
        List<Task> list = new ArrayList<>();
        for (int i = 0; i<checks.size(); i++){
            if (checks.get(i)){
                list.add(tasks.get(i));
            }
        }
        return list;
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView description;
        private TextView date;
        private CheckBox checkBox;
        private CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            description = (TextView) cardView.findViewById(R.id.text_description);
            date = (TextView) cardView.findViewById(R.id.text_date);
            checkBox = (CheckBox) cardView.findViewById(R.id.checkBox);
        }
    }
}
