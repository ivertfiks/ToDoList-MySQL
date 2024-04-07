package org.todolist;

import org.todolist.config.RunMode;
import org.todolist.controller.TaskController;
import org.todolist.service.DatabaseOperations;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();
        RunMode runMode = RunMode.DATABASE;
        taskController.setRunMode(runMode);
        try {
            taskController.run();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}