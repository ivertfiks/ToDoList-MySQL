package org.todolist.service;

import org.todolist.model.Task;

import java.util.List;

public interface TaskService {

    public void addTask(Task task);

    public void deleteTaskById(int id);

    public List<Task> getAllTasks();

    public void changeTaskStatus(int id, String status);
}
