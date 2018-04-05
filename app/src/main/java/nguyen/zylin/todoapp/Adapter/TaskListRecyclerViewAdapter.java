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

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder>{

    public interface TaskItemListener {
        void onItemClick();

        void onItemLongClick();
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
        final TaskListViewHolder viewHolder = new TaskListViewHolder(view);



        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: dialog edit, done
                listener.onItemClick();
            }
        });
        viewHolder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO: dialog delete
                listener.onItemLongClick();
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
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






    public static class TaskListViewHolder extends RecyclerView.ViewHolder {

        public TextView taskName, taskDescription, taskDeadline;
        public ImageView taskPriority;
        public RelativeLayout item;

        public TaskListViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.task_item_layout);
            taskName = itemView.findViewById(R.id.task_name);
            taskDescription = itemView.findViewById(R.id.task_description);
            taskDeadline = itemView.findViewById(R.id.task_deadline);
            taskPriority = itemView.findViewById(R.id.icon_priority);
        }
    }

}
