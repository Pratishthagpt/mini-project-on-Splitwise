package com.miniproject.splitwise.Command;

public interface Command {
    public void execute(String input);
    public boolean matches(String input);
}
