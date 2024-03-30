package org.todolist.model;

import org.todolist.model.enum_model.TaskPriority;
import org.todolist.model.enum_model.TaskStatus;

import java.util.Objects;

public class Task {
    private String title;
    private static int idCounter = 0;
    private int id;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;

    public Task(String task) {
        this.title = task;
        this.taskPriority = TaskPriority.LOW;
        idCounter++;
        id = idCounter;
        taskStatus = TaskStatus.PENDING;
    }

    public Task(int id, String title, TaskPriority taskPriority, TaskStatus taskStatus) {
        this.title = title;
        this.id = id;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return title;
    }

    public void setTask(String task) {
        this.title = task;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task1 = (Task) o;

        return Objects.equals(title, task1.title);
    }

    @Override
    public String toString() {
        return  "title='" + title + '\'' +
                ", id=" + id +
                ", taskPriority=" + taskPriority +
                ", taskStatus=" + taskStatus;
    }
}
