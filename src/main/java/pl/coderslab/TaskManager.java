package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void showList(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void Menu() {
        String option = "";
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        String[] options = {"add", "remove", "list", "exit"};
        for (String line : options) {
            System.out.println(ConsoleColors.RESET + line);
        }

    }

    public static String[][] loadFileTasks() throws IOException {

        String readLine = "";
        String[] task = new String[0];
        int countLines = 0;
        Path tasksPath = Paths.get("tasks.csv");
        boolean tasksPathExists = Files.exists(tasksPath);
        if (tasksPathExists) {
            for (String line : Files.readAllLines(tasksPath)) {
                readLine = line;
                if (!line.equals("")) {
                    task = Arrays.copyOf(task, task.length + 1);
                    task[task.length - 1] = readLine;
                }
            }
        } else {
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

    public static String[][] addTask(String[][] tasks) {
        String[][] newListTasks = new String[tasks.length + 1][3];
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                newListTasks[i][j] = tasks[i][j];
            }
        }
        Scanner scanner = new Scanner(System.in);
        String readLine = "";
        System.out.println("Please add task description:");
        newListTasks[tasks.length][0] = scanner.nextLine();
        System.out.println("Please add task due date");
        newListTasks[tasks.length][1] = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        newListTasks[tasks.length][2] = scanner.nextLine();
        return newListTasks;
    }

    public static String[][] removeTask(String[][] tasks, int indexRemove) {
        String[] allTasks;
        String task = "";
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                if (j == tasks[i].length - 1) {
                    task += tasks[i][j];
                } else {
                    task += tasks[i][j] + ",";
                }
            }
            task += "&&";
        }
        allTasks = task.split("&&");
        if(indexRemove <= tasks.length-1 && indexRemove > -1){
            String[][] newListTasks = new String[allTasks.length-1][3];
            allTasks = ArrayUtils.remove(allTasks, indexRemove);
            for (int i = 0; i < newListTasks.length; i++) {
                String[] splitTask = allTasks[i].split(",");
                for (int j = 0; j < newListTasks[i].length; j++) {
                    newListTasks[i][j] = splitTask[j];
                }
            }
            return newListTasks;

        }else{
            System.out.println("You put wrong index of tasks.");
            return tasks;
        }
    }


    public static void main(String[] args) throws IOException {

        String[][] tasks = loadFileTasks();
        showList(tasks);
        tasks = removeTask(tasks, 0);
        showList(tasks);

    }
}