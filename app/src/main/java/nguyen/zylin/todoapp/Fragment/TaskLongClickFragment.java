package nguyen.zylin.todoapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import nguyen.zylin.todoapp.R;

public class TaskLongClickFragment extends DialogFragment implements View.OnClickListener{

    public interface TaskLongClickListener{
        void okAction();

        void cancelAction();
    }
    private TaskLongClickListener listener;


    Button btnOK, btnCancel;

    public TaskLongClickFragment() {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.item_task_long_click_dialog,
                new LinearLayout(getActivity()), false);
        //Retrieve layout element
        btnOK = view.findViewById(R.id.delete_task_ok_btn); btnOK.setOnClickListener(this);
        btnCancel = view.findViewById(R.id.delete_task_cancel_btn); btnCancel.setOnClickListener(this);

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
            case R.id.delete_task_ok_btn:
                dismiss();
                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_task_cancel_btn:
                dismiss();
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
