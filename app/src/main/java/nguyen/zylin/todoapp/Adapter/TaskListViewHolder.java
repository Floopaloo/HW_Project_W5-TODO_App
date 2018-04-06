package nguyen.zylin.todoapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nguyen.zylin.todoapp.R;

public class TaskListViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener{

    public interface ItemListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }
    ItemListener listener;

    int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public TextView taskName, taskDescription, taskDeadline;
    public ImageView taskPriority;
    public RelativeLayout item;

    public TaskListViewHolder(View itemView, ItemListener listener) {
        super(itemView);
        this.listener = listener;
        item = itemView.findViewById(R.id.task_item_layout);
        taskName = itemView.findViewById(R.id.task_name);
        taskDescription = itemView.findViewById(R.id.task_description);
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
}
