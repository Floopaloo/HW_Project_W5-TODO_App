package nguyen.zylin.todoapp.Fragment;

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

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import nguyen.zylin.todoapp.Adapter.TaskListRecyclerViewAdapter;
import nguyen.zylin.todoapp.Model.TaskModel;
import nguyen.zylin.todoapp.R;

public class DoneFragment extends Fragment implements TaskListRecyclerViewAdapter.TaskItemListener{

    View view;
    private RecyclerView recyclerView;
    private TaskListRecyclerViewAdapter recyclerViewAdapter;
    private List<TaskModel> taskList = new ArrayList<>();
    Realm realm;

    public DoneFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.done_fragment, container, false);
        loadTask();
        recyclerView = view.findViewById(R.id.id_done_fragment_recycler_view);
        recyclerViewAdapter = new TaskListRecyclerViewAdapter(getContext(), taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadTask() {
        RealmResults<TaskModel> taskList =
                realm.where(TaskModel.class).equalTo("isDone", true).findAll();
        this.taskList.clear();
        this.taskList.addAll(taskList);
    }
    private void reloadTask() {
        RealmResults<TaskModel> taskList =
                realm.where(TaskModel.class).equalTo("isDone", true).findAll();
        recyclerViewAdapter.setData(taskList);
    }




///////////////////////////////////////////////////
    @Override
    public void onItemClick(TaskModel item) {

    }

    @Override
    public void onItemLongClick(TaskModel item) {

    }
}
