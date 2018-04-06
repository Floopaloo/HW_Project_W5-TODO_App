package nguyen.zylin.todoapp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nguyen.zylin.todoapp.Adapter.TaskListRecyclerViewAdapter;
import nguyen.zylin.todoapp.Model.TaskModel;
import nguyen.zylin.todoapp.R;

public class TaskFragment extends Fragment implements TaskListRecyclerViewAdapter.TaskItemListener,
        CreateTaskFragment.OnCreateTaskListener{

    public TaskFragment() {}

    View view;
    private RecyclerView recyclerView;
    private TaskListRecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton btnAddTask;
    private List<TaskModel> taskList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_fragment, container, false);
        btnAddTask = view.findViewById(R.id.id_fbtn_add_task);

        recyclerView = view.findViewById(R.id.id_task_fragment_recycler_view);
        recyclerViewAdapter = new TaskListRecyclerViewAdapter(getContext(), taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskModel a = new TaskModel("Test1", "say sth", "20/10", 1);
        TaskModel b = new TaskModel("Test2", "say sth", "20/10", 1);
        TaskModel c = new TaskModel("Test3", "say sth", "20/10", 1);
        taskList.add(a);
        taskList.add(b);
        taskList.add(c);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTaskDialog.show(getFragmentManager(),"createTaskDialog");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    TaskClickFragment clickDialog = new TaskClickFragment();
    TaskLongClickFragment longClickDialog = new TaskLongClickFragment();
    CreateTaskFragment createTaskDialog = new CreateTaskFragment();


    //Item click action
    @Override
    public void onItemClick() {
        clickDialog.show(getFragmentManager(), "taskClickDialog");
    }

    @Override
    public void onItemLongClick() {
        longClickDialog.show(getFragmentManager(), "taskLongClickDialog");
    }

    //Create task
    @Override
    public void onAddTask(String taskName, String taskDescription, String deadline, int priority) {

        if (taskName == null || taskName.isEmpty()) {
            showWarningDialog("Please type your task name!");
        } else {
            taskList.add(new TaskModel(taskName,taskDescription, deadline, priority));
            Toast.makeText(getActivity(), "Saving here", Toast.LENGTH_SHORT).show();
            //TODO: call save file here
        }
    }




    private void showWarningDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
