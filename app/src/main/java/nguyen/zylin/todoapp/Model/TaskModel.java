package nguyen.zylin.todoapp.Model;

public class TaskModel {

    public static final int TP_LOW = 1;
    public static final int TP_NORMAL = 2;
    public static final int TP_HIGH = 3;

    private String taskName, taskDescription, taskDeadline;
    private int taskPriority;

    public TaskModel(String taskName, String taskDescription, String taskDeadline, int taskPriority) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
        this.taskPriority = taskPriority;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }
}
