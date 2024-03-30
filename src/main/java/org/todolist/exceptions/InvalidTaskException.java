package org.todolist.exceptions;

public class InvalidTaskException extends Exception {
    public InvalidTaskException() {
        super();
    }

    public InvalidTaskException(String message) {
        super(message);
    }

    public InvalidTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTaskException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
