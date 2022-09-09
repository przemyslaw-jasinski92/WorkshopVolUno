package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static void Menu() throws IOException {
        String[][] tasks = loadFileTasks();
        Scanner scanner = new Scanner(System.in);
        String option = "";
        while (!option.equals("exit")) {
            System.out.println(ConsoleColors.BLUE + "Please select an option:");
            String[] options = {"add", "remove", "list", "exit"};
            for (String line : options) {
                System.out.println(ConsoleColors.RESET + line);
            }
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

    public static String[][] removeTask(String[][] tasks) {
        Scanner scanner = new Scanner(System.in);
        int indexRemove = -1;
        System.out.println("Select number to remove:");
        indexRemove = scanner.nextInt();
        while(indexRemove <= 0){

            if(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            }
            indexRemove = scanner.nextInt();
            if(indexRemove < 0 || indexRemove >= tasks.length){
                scanner.next();
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            }
        }
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
        String[][] newListTasks = new String[allTasks.length - 1][3];
        allTasks = ArrayUtils.remove(allTasks, indexRemove);
        for (int i = 0; i < newListTasks.length; i++) {
            String[] splitTask = allTasks[i].split(",");
            for (int j = 0; j < newListTasks[i].length; j++) {
                newListTasks[i][j] = splitTask[j];
            }
        }
        System.out.println("Value was successfully deleted");
        return newListTasks;
    }

    public static void saveToFile(String[][] tasks) throws IOException {
        Path tasksPath = Paths.get("tasks.csv");
        boolean tasksPathExists = Files.exists(tasksPath);
        if (tasksPathExists) {
            List<String> allTasks = new ArrayList<>();
            String[] splitTasks = new String[tasks.length];
            String task = "";
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    if (j == tasks[i].length - 1) {
                        task += tasks[i][j];
                    } else {
                        task += tasks[i][j] + ",";
                    }
                }
                splitTasks[i] = task;
                allTasks.add(splitTasks[i]);
                task = "";
            }
            try {
                Files.write(tasksPath, allTasks);
            } catch (IOException e) {
                System.err.println("Cannot save to file");
            }
        } else {
            throw new FileNotFoundException();
        }
    }


    public static void main(String[] args) throws IOException {

        String[][] tasks = loadFileTasks();
        Scanner scanner = new Scanner(System.in);
        String inputOption = "";
        while (!inputOption.equals("exit")) {
            System.out.println(ConsoleColors.BLUE + "Please select an option:");
            String[] options = {"add", "remove", "list", "exit"};
            for (String line : options) {
                System.out.println(ConsoleColors.RESET + line);
            }
            inputOption = scanner.nextLine();
            switch(inputOption){
                case "add":
                    tasks = addTask(tasks);
                    break;
                case "remove":
                    tasks = removeTask(tasks);
                    break;
                case "list":
                    showList(tasks);
                    break;
                case "exit":
                    saveToFile(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option");
            }
        }
    }
}