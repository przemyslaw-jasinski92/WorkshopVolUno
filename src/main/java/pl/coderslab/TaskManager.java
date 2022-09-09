package pl.coderslab;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TaskManager {

    public static void showTasks(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String showMenu(){
        String option = "";
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        String[] options = {"add", "remove", "list", "exit"};
        for (String line: options) {
            System.out.println(ConsoleColors.RESET + line);
        }
        return option;
    }

    public static String[][] tasks() throws IOException{

        String readLine = "";
        String[] task = new String[0];
        int countLines = 0;
        Path tasksPath = Paths.get("tasks.csv");
        boolean tasksPathExists = Files.exists(tasksPath);
        if(tasksPathExists) {
            for (String line : Files.readAllLines(tasksPath)) {
                readLine = line;
                if (!line.equals("")) {
                    task = Arrays.copyOf(task, task.length + 1);
                    task[task.length - 1] = readLine;
                }
            }
        }else{
            throw new FileNotFoundException();
        }
        String[][] tasks = new String[task.length][3];
        for (int i = 0; i < tasks.length; i++) {
            String[] splitTasks = task[i].split(",");
            for (int j = 0; j < tasks[i].length; j++) {
                tasks[i][j] = splitTasks[j];
            }
        }
        return tasks;
    }


    public static void main(String[] args) throws IOException{

        showTasks(tasks());
    }
}