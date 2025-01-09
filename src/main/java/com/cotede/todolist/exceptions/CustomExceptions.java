package com.cotede.todolist.exceptions;

public class CustomExceptions {

    public static class UserNotFound extends RuntimeException {
        public UserNotFound(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String username) {
            super(username);
        }

    }

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String email) {
            super(email);
        }

    }

    public static class ToDoNotFound extends RuntimeException {
        public ToDoNotFound(String Title) {
            super(Title);
        }
    }
}
