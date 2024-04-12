package org.todolist.controller;

import org.todolist.config.RunMode;
import org.todolist.exceptions.enum_exception.ExceptionMessage;
import org.todolist.model.Task;
import org.todolist.model.enum_model.TaskPriority;
import org.todolist.model.enum_model.TaskStatus;
import org.todolist.service.TaskService;
import org.todolist.service.console.ConsoleService;
import org.todolist.service.database.DatabaseService;
import org.todolist.service.files.FileOperations;
import org.todolist.service.files.FileService;
import org.todolist.service.files.TaskListService;
import org.todolist.view.TaskView;
import org.todolist.view.enum_view.UserCommands;

import java.io.IOException;
import java.util.List;

public class FileTaskController {

    private TaskService taskService;
    private TaskView taskView;
    private RunMode runMode;

    public FileTaskController(RunMode mode) {
        this.runMode = mode;
        this.taskView = new TaskView();
    }

    public void run() {

        boolean isRunnable = true;

        while (isRunnable) {
            taskView.showMenu(); // prints user menu
            int number = taskView.getInt(); // get user response

            if (runMode.equals(RunMode.FILE_PARSING)) {
                taskService = new FileService();
            } else if (runMode.equals(RunMode.CONSOLE)) {
                taskService = new ConsoleService();
            } else {
                taskService = new DatabaseService();
            }

            switch (number) {
                case 1:
                    addTask();
                    break;
                case 2:
                    deleteTask();
                    break;
                case 3:
                    updateStatus();
                    break;
                case 4:
                    showTasks();
                    break;
                case 5:
                    isRunnable = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void showTasks() {
        for (Task task : taskService.getAllTasks()) {
            taskView.showString(task.toString());
        }
    }

    private void updateStatus() {
        taskView.showString(UserCommands.EDIT_STATUS.getCommandName());
        int id = taskView.getInt();
        taskView.showString(UserCommands.ENTER_STATUS.getCommandName());
        taskView.clearBuffer();
        String status = taskView.getString();
        for (Task task : taskService.getAllTasks()) {
            if (task.getId() == id) {
                task.setTaskStatus(TaskStatus.valueOf(status.toUpperCase()));
            }
        }
    }

    private void addTask() {
        Task taskToAdd = getTaskInfo();
        taskService.addTask(taskToAdd);
    }

    private void deleteTask() {
        taskView.showString(UserCommands.REMOVE_NUMBER.getCommandName());
        int id = taskView.getInt();
        for (Task task : taskService.getAllTasks()) {
            if (task.getId() == id) {
                taskService.deleteTaskById(task.getId());
                return;
            }
        }
    }

    private Task getTaskInfo() {
        taskView.showString(UserCommands.ENTER_TASK.getCommandName());
        String task = taskView.getString();
        return new Task(task);
    }
}
