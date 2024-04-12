package org.todolist.service.files;

import org.todolist.model.Task;
import org.todolist.model.enum_model.TaskPriority;
import org.todolist.model.enum_model.TaskStatus;

import java.io.*;

public class FileOperations {
    private TaskListService taskService;

    public FileOperations(TaskListService taskService) {
        this.taskService = taskService;
    }

    public void writeInFile(TaskListService taskService, String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id;").append("name;").append("taskPriority").append("\n");
        File csvFile = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(csvFile)) {
            for (Task task : taskService.getTaskList()) {
                stringBuilder.append(task.getId());
                stringBuilder.append(";");
                stringBuilder.append(task.getTask());
                stringBuilder.append(";");
                stringBuilder.append(task.getTaskPriority());
                stringBuilder.append(";");
                stringBuilder.append(task.getTaskStatus());
                stringBuilder.append("\n");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void addTaskToFile(Task task, String fileName){
        taskService.addTask(task);
        try {
            writeInFile(taskService, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFromFile(int id){
        for(Task task : taskService.getTaskList()){
            if(task.getId() == id){
                taskService.removeTask(task);
                try {
                    writeInFile(taskService, FileQueries.WRITE_IN_FILE_NAME);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
    }

//    public void addSingleTaskToFile(Task task, String fileName) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("id;").append("name;").append("taskPriority").append("\n");
//        File csvFile = new File(fileName);
//        try (FileWriter fileWriter = new FileWriter(csvFile)) {
//            stringBuilder.append(task.getId())
//                    .append(";")
//                    .append(task.getTask())
//                    .append(";")
//                    .append(task.getTaskPriority())
//                    .append(";")
//                    .append(task.getTaskStatus())
//                    .append("\n");
//            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//            fileWriter.write(stringBuilder.toString());
//        } catch (IOException e) {
//            throw new IOException(e);
//        }
//    }

    public void readFromFile(String path) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] taskStringParts = line.split(";"); // [1][Develop Feature A][HIGH]
                Task task = stringToTask(taskStringParts);
                taskService.addTask(task);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Task stringToTask(String[] taskStringParts) {
        int id = Integer.parseInt(taskStringParts[0]);
        String taskTitle = taskStringParts[1];
        TaskPriority taskPriority = TaskPriority.valueOf(taskStringParts[2].toUpperCase());
        TaskStatus taskStatus = TaskStatus.valueOf(taskStringParts[3].toUpperCase());
        return new Task(id, taskTitle, taskPriority, taskStatus);
    }
}
