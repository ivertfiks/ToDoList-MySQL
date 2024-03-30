package org.todolist;

import org.todolist.config.RunMode;
import org.todolist.controller.TaskController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();
        RunMode runMode = RunMode.CONSOLE;
        taskController.setRunMode(runMode);
        try {
            taskController.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}