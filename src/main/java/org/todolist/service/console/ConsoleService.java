package org.todolist.service.console;

import org.todolist.model.Task;
import org.todolist.service.TaskService;
import org.todolist.service.files.TaskListService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleService implements TaskService {

    TaskListService taskListService = new TaskListService();

    @Override
    public void addTask(Task task) {
        taskListService.addTask(task);
    }

    @Override
    public void deleteTaskById(int id) {
        for (Task task : taskListService.getTaskList()) {
            if (task.getId() == id) {
                taskListService.removeTask(task);
                return;
            }
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return taskListService.getTaskList();
    }

    @Override
    public void changeTaskStatus(int id, String status) {
        try {
            taskListService.setStatus(id, status);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
