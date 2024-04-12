package org.todolist.service.files;

public class FileQueries {

    private static String table;

    public FileQueries(String table) {
        FileQueries.table = table;
    }

    public static final String WRITE_IN_FILE_NAME = "task.csv";

}
