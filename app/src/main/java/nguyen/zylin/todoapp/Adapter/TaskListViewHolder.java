package nguyen.zylin.todoapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nguyen.zylin.todoapp.Model.TaskModel;
import nguyen.zylin.todoapp.R;

public class TaskListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private TextView taskName, taskDescription, taskDeadline;
    private ImageView taskPriority;
    private Context context;
    private int position;

    public interface TaskListViewHolderListener {
        void onItemClick(int position);

        void onItemLongClick(int position);

    }

    private TaskListViewHolderListener listener;

    public TaskListViewHolder(View itemView, TaskListViewHolderListener listener) {
        super(itemView);
        this.listener = listener;
        context = itemView.getContext();
        taskName = itemView.findViewById(R.id.task_name);
        taskName = itemView.findViewById(R.id.task_description);
        taskDeadline = itemView.findViewById(R.id.task_deadline);
        taskPriority = itemView.findViewById(R.id.icon_priority);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(position);
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onItemLongClick(position);
        return true;
    }

    public void createTaskList(TaskModel taskModel, int position) {
        this.position = position;
        taskName.setText(taskModel.getTaskName());
        taskDescription.setText(taskModel.getTaskDescription());
        taskDeadline.setText("Deadline: " + taskModel.getTaskDeadline());

        switch (taskModel.getTaskPriority()) {
            case TaskModel.TP_HIGH:
                taskPriority.setImageResource(R.drawable.circle_priority_high);
                break;
            case TaskModel.TP_NORMAL:
                taskPriority.setImageResource(R.drawable.circle_priority_normal);
                break;
            case TaskModel.TP_LOW:
                taskPriority.setImageResource(R.drawable.circle_priority_low);
                break;
        }
    }
}
