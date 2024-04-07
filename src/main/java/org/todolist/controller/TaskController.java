package org.todolist.controller;

import org.todolist.config.RunMode;
import org.todolist.exceptions.enum_exception.ExceptionMessage;
import org.todolist.model.Task;
import org.todolist.model.enum_model.TaskPriority;
import org.todolist.model.enum_model.TaskStatus;
import org.todolist.service.DatabaseOperations;
import org.todolist.service.FileOperations;
import org.todolist.service.TaskService;
import org.todolist.view.TaskView;
import org.todolist.view.enum_view.UserCommands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class TaskController {
    private TaskService taskService;
    private TaskView taskView;
    private FileOperations fileOperations;
    private RunMode runMode;

    public TaskController() {
        this.taskService = new TaskService();
        this.taskView = new TaskView();
        this.fileOperations = new FileOperations(taskService);
    }

    public void showTasks() {
        for (Task task : taskService.getTaskList()) {
            taskView.showString(task.toString());
        }
    }

    public void removeTask(int id) {
        for (Task task : taskService.getTaskList()) {
            if (task.getId() == id) {
                taskService.removeTask(task);
                return;
            }
        }
    }

    public void changeStatus() throws IOException{
        taskView.showString(UserCommands.EDIT_STATUS.getCommandName());
        int id = taskView.getInt();
        taskView.showString(UserCommands.ENTER_STATUS.getCommandName());
        taskView.clearBuffer();
        String status = taskView.getString();
        for (Task task : taskService.getTaskList()) {
            if (task.getId() == id) {
                task.setTaskStatus(TaskStatus.valueOf(status.toUpperCase()));
            }
        }
    }

    public void setRunMode(RunMode runMode) {
        this.runMode = runMode;
    }

    public void run() throws IOException, SQLException {
        if (runMode.equals(RunMode.CONSOLE)) {
            runScanner();
        } else if (runMode.equals(RunMode.FILE_PARSING)) {
            runFileParser();
        }else if(runMode.equals(RunMode.DATABASE)){
            runDatabaseOperations();
        };
    }

    public void runScanner() throws IOException {
        boolean isRunnable = true;
        while (isRunnable) {
            taskView.showMenu();
            int number = taskView.getInt();
            taskView.clearBuffer();
            switch (number) {
                case 1:
                    taskView.showString(UserCommands.ENTER_TASK.getCommandName());
                    String task = taskView.getString();
                    taskView.showString(UserCommands.ENTER_PRIORITY.getCommandName());
                    String priority = taskView.getString().toUpperCase();
                    try{
                        TaskPriority taskPriority = TaskPriority.valueOf(priority);
                    }catch (IllegalArgumentException e){
                        taskView.showString(ExceptionMessage.ILLEGAL_ARGUMENT_EXCEPTION.getCommandName());
                    }
                    taskService.addTask(new Task(task));
                    break;
                case 2:
                    showTasks();
                    try {
                        taskView.showString(UserCommands.REMOVE_NUMBER.getCommandName());
                        removeTask(taskView.getInt());
                    } catch (InputMismatchException | IndexOutOfBoundsException exception) {
                        taskView.clearBuffer();
                        if (exception instanceof InputMismatchException) {
                            taskView.showString(ExceptionMessage.INPUT_MISMATCH_EXCEPTION.getCommandName());
                        } else if (exception instanceof IndexOutOfBoundsException) {
                            taskView.showString(ExceptionMessage.INDEX_OUT_OF_BOUNCE_EXCEPTION.getCommandName());
                        } else {
                            taskView.showString(ExceptionMessage.UNEXPECTED_ERROR.getCommandName());
                        }
                    }
                    break;
                case 3:
                    changeStatus();
                    break;
                case 4:
                    showTasks();
                    break;
                case 5:
                    isRunnable = false;
                    break;
                default:
                    taskView.showString(ExceptionMessage.WRONG_MENU_NUMBER.getCommandName());
                    break;
            }
        }
    }
    public void runFileParser() throws IOException {
        try {
            fileOperations.readFromFile("tasks.csv");
        } catch (FileNotFoundException e) {
            taskView.showString(ExceptionMessage.FILE_NOT_FOUND_EXCEPTION.getCommandName());
        }
        taskView.showString(UserCommands.ENTER_WRITE_FILE_NAME.getCommandName());
        String writeInFileName = taskView.getString();
        boolean isRunnable = true;
        while (isRunnable) {
            taskView.showMenu();
            int number = taskView.getInt();
            taskView.clearBuffer();
            switch (number) {
                case 1:
                    taskView.showString(UserCommands.ENTER_TASK.getCommandName());
                    taskService.addTask(new Task(taskView.getString()));
                    break;
                case 2:
                    showTasks();
                    taskView.showString(UserCommands.REMOVE_NUMBER.getCommandName());
                    try {
                        removeTask(taskView.getInt());
                        fileOperations.writeInFile(taskService, writeInFileName);
                    } catch (InputMismatchException | IndexOutOfBoundsException | IOException exception) {
                        taskView.clearBuffer();
                        if (exception instanceof InputMismatchException) {
                            taskView.showString(ExceptionMessage.INPUT_MISMATCH_EXCEPTION.getCommandName());
                        } else if (exception instanceof IndexOutOfBoundsException) {
                            taskView.showString(ExceptionMessage.INDEX_OUT_OF_BOUNCE_EXCEPTION.getCommandName());
                        } else {
                            taskView.showString(ExceptionMessage.UNEXPECTED_ERROR.getCommandName());
                        }
                    }
                    break;
                case 3:
                    changeStatus();
                    break;
                case 4:
                    showTasks();
                    break;
                case 5:
                    isRunnable = false;
                    break;
                default:
                    taskView.showString(ExceptionMessage.WRONG_MENU_NUMBER.getCommandName());
                    break;
            }
        }
    }
    public void runDatabaseOperations() throws IOException, SQLException {
        String address = "127.0.0.1:3306";
        String userName = "root";
        String password = "password";
        String tableName = "todo_list";
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(address, userName, password);
        boolean isRunnable = true;
        while (isRunnable) {
            taskView.showMenu();
            int number = taskView.getInt();
            taskView.clearBuffer();
            switch (number) {
                case 1:
                    taskView.showString(UserCommands.ENTER_TASK.getCommandName());
                    String tempTaskTitle = taskView.getString();
                    taskView.showString(UserCommands.ENTER_PRIORITY.getCommandName());
                    TaskPriority taskPriority = TaskPriority.valueOf(taskView.getString().toUpperCase());
                    databaseOperations.addTask(tableName, tempTaskTitle, taskPriority.getPriority(), TaskStatus.PENDING.getPriority());
                    break;
                case 2:
                    showTasks();
                    taskView.showString(UserCommands.REMOVE_NUMBER.getCommandName());
                    databaseOperations.deleteTask(tableName, taskView.getInt());
                    break;
                case 3:
                    taskView.showString(UserCommands.CHANGE_NUMBER.getCommandName());
                    int temp = taskView.getInt();
                    taskView.showString(UserCommands.ENTER_STATUS.getCommandName());
                    taskView.clearBuffer();
                    TaskStatus tempStatus = TaskStatus.valueOf(taskView.getString().toUpperCase());
                    databaseOperations.updateTask(tableName, temp, tempStatus);
                    break;
                case 4:
                    databaseOperations.printTasks(tableName);
                    break;
                case 5:
                    isRunnable = false;
                    break;
                default:
                    taskView.showString(ExceptionMessage.WRONG_MENU_NUMBER.getCommandName());
                    break;
            }
        }
    }
}
