package org.todolist.model.enum_model;

public enum TaskStatus {
    PENDING("Pending"),
    DOING("Doing"),
    DONE("Done");


    TaskStatus(String priority) {
        this.status = priority;
    }

    String status;

    public String getStatus() {
        return status;
    }
}
