package org.todolist.view;

import org.todolist.view.enum_view.MenuCommands;

import java.util.Scanner;

public class TaskView {
    private Scanner input;

    public TaskView() {
        input = new Scanner(System.in);
    }

    public void showMenu(){
        for(MenuCommands menuCommands : MenuCommands.values()){
            System.out.println(menuCommands.commandName);
        }
    }

    public int getInt(){
        return input.nextInt();
    }

    public String getString(){
        return input.nextLine();
    }
    public void showString(String string){
        System.out.println(string);
    }

    public void clearBuffer(){
        input.nextLine();
    }
}
