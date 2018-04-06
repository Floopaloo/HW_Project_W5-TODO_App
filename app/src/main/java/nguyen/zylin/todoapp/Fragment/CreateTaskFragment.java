package nguyen.zylin.todoapp.Fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

import nguyen.zylin.todoapp.Model.TaskModel;
import nguyen.zylin.todoapp.R;

import static android.content.ContentValues.TAG;

public class CreateTaskFragment extends DialogFragment implements View.OnClickListener{

    public interface OnCreateTaskListener {
        void onAddTask(String taskName, String taskDescription, String deadline, int priority);
    }
    OnCreateTaskListener listener;


    EditText taskName, taskDescription;
    DatePicker timePicker;
    RadioGroup radioGroup;
    Button btnSave, btnCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_edit_task_fragment, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskName = view.findViewById(R.id.create_edit_dialog_edt_task_name);
        taskDescription = view.findViewById(R.id.create_edit_dialog_edt_task_description);
        timePicker = view.findViewById(R.id.create_edit_dialog_datePicker);
        radioGroup = view.findViewById(R.id.create_edit_dialog_radioGroup);

        btnSave = view.findViewById(R.id.create_edit_dialog_btn_save); btnSave.setOnClickListener(this);
        btnCancel = view.findViewById(R.id.create_edit_dialog_btn_cancel); btnCancel.setOnClickListener(this);

        setupPriorityRadioBtn();
        setupDatePicker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.create_edit_dialog_btn_save:
                dismiss();
                Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();
                createTask();
                break;

            case R.id.create_edit_dialog_btn_cancel:
                dismiss();
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    //Set size of dialog
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    //Create task
    private void createTask() {
        String taskName = this.taskName.getText().toString().trim();
        String taskDescription = this.taskDescription.getText().toString().trim();
        String taskDeadline = String.format("%s%s%s", day, month, year);
        listener = (OnCreateTaskListener) getActivity();
        listener.onAddTask(taskName,taskDescription,taskDeadline,taskPriority);
    }


    private int taskPriority = TaskModel.TP_NORMAL;
    private void setupPriorityRadioBtn(){
        radioGroup.check(R.id.create_edit_dialog_rbtn_priority_normal);
        radioGroup.setOnCheckedChangeListener(notifyCheckChanged);
    }

    private RadioGroup.OnCheckedChangeListener notifyCheckChanged = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.create_edit_dialog_rbtn_priority_high:
                    taskPriority = TaskModel.TP_HIGH;
                    break;
                case R.id.create_edit_dialog_rbtn_priority_low:
                    taskPriority = TaskModel.TP_LOW;
                    break;
                default:
                    taskPriority = TaskModel.TP_NORMAL;
                    break;
            }
        }
    };


    private int year;
    private int month;
    private int day;
    private void setupDatePicker(){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        timePicker.init(year, month, day, notifyTimeChanged);
    }
    private DatePicker.OnDateChangedListener notifyTimeChanged = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
        }
    };
}
