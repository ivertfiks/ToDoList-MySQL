package org.todolist;

import org.todolist.config.RunMode;
import org.todolist.controller.FileTaskController;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        FileTaskController taskController = new FileTaskController(RunMode.DATABASE);
        taskController.run();
    }

}