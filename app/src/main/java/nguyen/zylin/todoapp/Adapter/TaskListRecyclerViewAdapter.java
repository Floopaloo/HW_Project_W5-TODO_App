package nguyen.zylin.todoapp.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import nguyen.zylin.todoapp.Fragment.TaskClickFragment;
import nguyen.zylin.todoapp.Model.TaskModel;
import nguyen.zylin.todoapp.R;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListViewHolder>
        implements TaskListViewHolder.ItemListener{

    public interface TaskItemListener {
        void onItemClick(TaskModel item);

        void onItemLongClick(TaskModel item);
    }

    private TaskItemListener listener;


    Context context;
    List<TaskModel> taskList = new ArrayList<>();

    public TaskListRecyclerViewAdapter(Context context, List<TaskModel> taskList, TaskItemListener listener) {
        this.context = context;
        this.taskList.clear();
        this.taskList.addAll(taskList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_layout, parent, false);
        final TaskListViewHolder viewHolder = new TaskListViewHolder(view, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        holder.setPosition(position);
        TaskModel task = taskList.get(position);
        holder.taskName.setText(task.getTaskName());
        holder.taskDescription.setText(task.getTaskDescription());
        holder.taskDeadline.setText("Deadline: " + task.getTaskDeadline());

        switch (task.getTaskPriority()) {
            case TaskModel.TP_HIGH:
                holder.taskPriority.setImageResource(R.drawable.circle_priority_high);
                break;
            case TaskModel.TP_NORMAL:
                holder.taskPriority.setImageResource(R.drawable.circle_priority_normal);
                break;
            case TaskModel.TP_LOW:
                holder.taskPriority.setImageResource(R.drawable.circle_priority_low);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }


    public void setData(List<TaskModel> taskList) {
        this.taskList.clear();
        this.taskList.addAll(taskList);
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        TaskModel item = taskList.get(position);
        listener.onItemClick(item);
    }

    @Override
    public void onItemLongClick(int position) {
        TaskModel item = taskList.get(position);
        listener.onItemLongClick(item);
    }

}
