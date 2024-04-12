package org.todolist.service.files;

import org.todolist.model.Task;
import org.todolist.service.TaskService;

import java.io.IOException;
import java.util.List;

public class FileService implements TaskService {
    FileOperations fileOperations;
    public FileService(){

    }

    @Override
    public void addTask(Task task) {
        fileOperations.addTaskToFile(task, FileQueries.WRITE_IN_FILE_NAME);
    }

    @Override
    public void deleteTaskById(int id) {
        fileOperations.deleteFromFile(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public void changeTaskStatus(int id, String status) {

    }
}
