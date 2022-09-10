package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
        if (tasks.length == 0) {
            System.out.println("List of tasks is empty.");
        } else {
            for (int i = 0; i < tasks.length; i++) {
                System.out.print(i + " : ");
                for (int j = 0; j < tasks[i].length; j++) {
                    System.out.print(tasks[i][j] + " ");
                }
                System.out.println();
            }
        }

    }

    public static void showMenu() throws IOException {
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
            inputOption = inputOption.toLowerCase();
            switch (inputOption) {
                case "add" -> tasks = addTask(tasks);
                case "remove" -> tasks = removeTask(tasks);
                case "list" -> showList(tasks);
                case "exit" -> {
                    saveToFile(tasks);
                    System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Bye, bye:*");
                }
                default -> System.out.println("Please select a correct option");
            }
        }

    }

    public static String[][] loadFileTasks() throws IOException {

        String readLine = "";
        String[] task = new String[0];
        Path tasksPath = Paths.get("tasks.csv");
        boolean tasksPathExists = Files.exists(tasksPath);
        if (!tasksPathExists) {
            throw new FileNotFoundException();
        }
        for (String line : Files.readAllLines(tasksPath)) {
            readLine = line;
            if (!line.equals("")) {
                task = Arrays.copyOf(task, task.length + 1);
                task[task.length - 1] = readLine;
            }
        }

        String[][] tasks = new String[task.length][3];
        for (int i = 0; i < tasks.length; i++) {
            String[] splitTasks = task[i].split(",");
            for (int j = 0; j < 3; j++) {
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

        /*tasks = Arrays.copyOf(tasks, tasks.length + 1);
        System.out.println(Arrays.toString(tasks));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description:");
        tasks[tasks.length-1][0] = scanner.nextLine();
        while (tasks[tasks.length-1][0].contains("&&")) {
            System.out.println("You put forbidden signs like '&&'. Please add task description without '&&'");
            tasks[tasks.length-1][0] = scanner.nextLine();
        }
        System.out.println("Please add task due date");
        tasks[tasks.length-1][1] = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        tasks[tasks.length-1][2] = scanner.next();
        while (!(tasks[tasks.length-1][2].equals("true") || tasks[tasks.length-1][2].equals("false"))) {
            System.out.println("You chose wrong importance. Enter: true/false:");
            tasks[tasks.length-1][2] = scanner.next();
        }*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description:");
        newListTasks[tasks.length][0] = scanner.nextLine();
        System.out.println("Please add task due date");
        newListTasks[tasks.length][1] = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        newListTasks[tasks.length][2] = scanner.next();
        while (!(newListTasks[tasks.length][2].equals("true") || newListTasks[tasks.length][2].equals("false"))) {
            System.out.println("You chose wrong importance. Enter: true/false:");
            newListTasks[tasks.length][2] = scanner.next();
        }

        return newListTasks;
    }

    public static String[][] removeTask(String[][] tasks) {
        Scanner scanner = new Scanner(System.in);
        String checkIndexRemove = "";
        int indexRemove = -1;
        if (tasks.length == 0) {
            System.out.println("List of tasks is empty.");
            return tasks;
        }
        System.out.println("Select number to remove:");
        do {
            checkIndexRemove = scanner.next();
            if (!NumberUtils.isParsable(checkIndexRemove)) {
                System.out.println("Wrong value of index to remove. Enter properly index value:");
                scanner.nextLine();
            } else {
                indexRemove = Integer.parseInt(checkIndexRemove);
                if (indexRemove < 0 || indexRemove > tasks.length - 1) {
                    System.out.println("Index value is out of range. Enter properly index value:");
                    scanner.nextLine();
                }
            }
        } while (checkIndexRemove.equals("") || !NumberUtils.isParsable(checkIndexRemove) || indexRemove < 0 || indexRemove > tasks.length - 1);


        tasks = ArrayUtils.remove(tasks, indexRemove);
        System.out.println("Value was successfully deleted");
        return tasks;
    }

    public static void saveToFile(String[][] tasks) throws IOException {
        Path tasksPath = Paths.get("tasks.csv");
        boolean tasksPathExists = Files.exists(tasksPath);
        if (!tasksPathExists) {
            throw new FileNotFoundException();
        }
        List<String> allTasks = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                if (j == tasks[i].length - 1) {
                    sb.append(tasks[i][j]);
                } else {
                    sb.append(tasks[i][j] + ",");
                }
            }
            allTasks.add(sb.toString());
            sb.delete(0, sb.length());
        }
        try {
            Files.write(tasksPath, allTasks);
        } catch (IOException e) {
            System.err.println("Cannot save to file");
        }
    }


    public static void main(String[] args) throws IOException {
        showMenu();
    }
}