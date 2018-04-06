package nguyen.zylin.todoapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import nguyen.zylin.todoapp.Model.TaskModel;
import nguyen.zylin.todoapp.R;

public class TaskClickFragment extends DialogFragment implements View.OnClickListener{

    public interface TaskClickListener{
        void editAction(TaskModel item);

        void doneAction(TaskModel item);
    }
    private TaskClickListener listener;

    public void setTaskClickListener(TaskClickListener listener) {
        this.listener = listener;
    }


    Button editBtn, doneBtn;
    TaskModel item;

    public void setItem(TaskModel item) {
        this.item = item;
    }

    public TaskClickFragment() {
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.item_task_click_dialog,
                new LinearLayout(getActivity()), false);
        //Retrieve layout element
        editBtn = view.findViewById(R.id.btn_edit); editBtn.setOnClickListener(this);
        doneBtn = view.findViewById(R.id.btn_done); doneBtn.setOnClickListener(this);

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
//                dismiss();
//                Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
                listener.editAction(this.item);
                break;
            case R.id.btn_done:
//                dismiss();
//                Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                listener.doneAction(this.item);
                break;
        }
    }
}
