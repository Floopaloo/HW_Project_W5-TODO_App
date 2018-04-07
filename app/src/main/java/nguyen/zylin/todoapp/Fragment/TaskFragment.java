package nguyen.zylin.todoapp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import nguyen.zylin.todoapp.Adapter.TaskListRecyclerViewAdapter;
import nguyen.zylin.todoapp.Model.TaskModel;
import nguyen.zylin.todoapp.R;

public class TaskFragment extends Fragment implements TaskListRecyclerViewAdapter.TaskItemListener,
        CreateTaskFragment.OnCreateTaskListener, TaskClickFragment.TaskClickListener,
        EditTaskFragment.OnEditTaskListener, TaskLongClickFragment.TaskLongClickListener {

    public TaskFragment() {}

    View view;
    private RecyclerView recyclerView;
    private TaskListRecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton btnAddTask;
    private List<TaskModel> taskList = new ArrayList<>();

    Realm realm;

    TaskClickFragment clickDialog;
    TaskLongClickFragment longClickDialog;
    CreateTaskFragment createTaskDialog;
    EditTaskFragment editTaskFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_fragment, container, false);
        btnAddTask = view.findViewById(R.id.id_fbtn_add_task);

        loadTask();
        recyclerView = view.findViewById(R.id.id_task_fragment_recycler_view);
        recyclerViewAdapter = new TaskListRecyclerViewAdapter(getContext(), taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clickDialog = new TaskClickFragment();
        clickDialog.setTaskClickListener(TaskFragment.this);

        longClickDialog = new TaskLongClickFragment();
        longClickDialog.setTaskLongClickListener(this);

        createTaskDialog = new CreateTaskFragment();
        createTaskDialog.setOnCreateTaskListener(TaskFragment.this);

        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
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

    //Create task
    @Override
    public void onAddTask(String taskName, String taskDescription, String deadline, int priority) {

        if (taskName == null || taskName.isEmpty()) {
            showWarningDialog("Please type your task name!");
        } else {
            //TODO: call save file here
            createTask(taskName,taskDescription, deadline, priority);
            Toast.makeText(getActivity(), "Saving...", Toast.LENGTH_SHORT).show();
            reloadTask();
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


    private void createTask(final String taskName, final String taskDescription,
                            final String taskDeadline, final int taskPriority) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskModel task = realm.createObject(TaskModel.class);
                task.setId(generateID());
                task.setTaskName(taskName);
                task.setTaskDescription(taskDescription);
                task.setTaskDeadline(taskDeadline);
                task.setTaskPriority(taskPriority);
            }
        });
    }

    private void loadTask() {
        RealmResults<TaskModel> taskList =
                realm.where(TaskModel.class).equalTo("isDone", false).findAll();
        this.taskList.clear();
        this.taskList.addAll(taskList);
    }

    private void reloadTask() {
        RealmResults<TaskModel> taskList =
                realm.where(TaskModel.class).equalTo("isDone", false).findAll();
        recyclerViewAdapter.setData(taskList);
    }

    private void makeDoneTask(long taskID) {
        final TaskModel task = realm.where(TaskModel.class)
                .equalTo("id", taskID)
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setDone(true);
            }
        });
    }

    private void updateTask(long taskId, final String taskName, final String taskDescription,
                            final String taskDeadline, final int taskPriority) {
        final TaskModel taskUpdate = realm.where(TaskModel.class)
                .equalTo("id", taskId)
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                taskUpdate.setTaskName(taskName);
                taskUpdate.setTaskDescription(taskDescription);
                taskUpdate.setTaskDeadline(taskDeadline);
                taskUpdate.setTaskPriority(taskPriority);
            }
        });
    }

    private void deleteTask(TaskModel taskModel) {
        final TaskModel taskDelete = realm.where(TaskModel.class)
                .equalTo("id", taskModel.getId())
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                taskDelete.deleteFromRealm();
            }
        });
    }

    private long generateID() {
        Random gen = new Random();
        return gen.nextLong();
    }

    //Item action
    @Override
    public void onItemClick(TaskModel item) {
        clickDialog.setItem(item);
        clickDialog.show(getFragmentManager(),"clickDialog");
    }

    @Override
    public void onItemLongClick(TaskModel item) {
        longClickDialog.setItem(item);
        longClickDialog.show(getFragmentManager(),"longClickDialog");
    }



    //Click dialog action
    @Override
    public void editAction(TaskModel item) {
        clickDialog.dismiss();
        //TODO: show edit dialog
        editTaskFragment = new EditTaskFragment().newInstance(item);
        editTaskFragment.setOnCreateTaskListener(TaskFragment.this);
        editTaskFragment.show(getFragmentManager(),"editTaskFragment");
    }

    @Override
    public void doneAction(TaskModel item) {
        clickDialog.dismiss();
        //TODO: Set task status to done
        makeDoneTask(item.getId());
        reloadTask();
        Intent intent = new Intent(DoneFragment.DATA_SET_CHANGED);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onUpdateTask(long taskID, String taskName, String taskDescription, String taskDeadline, int taskPriority) {
        if (taskName == null || taskName.isEmpty()) {
            showWarningDialog("Please type your task name!");
        } else {
            //TODO: call save file here
            updateTask(taskID, taskName, taskDescription, taskDeadline,taskPriority);
            Toast.makeText(getActivity(), "Saving...", Toast.LENGTH_SHORT).show();
            reloadTask();
        }
    }

    //Long click action
    @Override
    public void okAction(TaskModel item) {
        longClickDialog.dismiss();
        deleteTask(item);
        reloadTask();
    }

    @Override
    public void cancelAction(TaskModel item) {
        longClickDialog.dismiss();
    }

}
