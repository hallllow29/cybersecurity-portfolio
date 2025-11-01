package Core;

import Enums.Priority;
import lib.lists.ArrayUnorderedList;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * MainMenu is the entry point for a system that simulates task management
 * and resource monitoring operations. It provides a menu-based interface
 * to manage tasks, interact with the CreepingSystem, and display memory usage.
 * The class contains static methods to handle user input, add tasks, display
 * memory usage, and save system state to a JSON file.
 */
public class MainMenu {

    /**
     * A LinkedList to store memory usage data, represented as integers.
     * This list is used to record or analyze memory usage over a period of time.
     */
    private static LinkedList<Integer> memoryUsageData = new LinkedList<>();

    /**
     * The `tasks` variable is a static instance of `ArrayUnorderedList<Task>`
     * that holds a collection of tasks. It serves as a central storage for task
     * management within the `MainMenu` class.
     */
    private static ArrayUnorderedList<Task> tasks = new ArrayUnorderedList<>();

    /**
     * Displays and manages the main menu for interacting with the Creeping System application.
     * This method provides a console-based user interface that allows users to perform various
     * actions on the Creeping System, such as starting/stopping the system, adding tasks,
     * viewing memory usage, saving system data to JSON, or exiting the menu.
     *
     * The main menu operates in a loop until the user chooses to exit. Based on the selected
     * option, the appropriate functionality is triggered.
     *
     * Menu Options:
     * 1. Start the Creeping System.
     * 2. Stop the Creeping System.
     * 3. Add a task to the system.
     * 4. Display a chart of memory usage data.
     * 5. Save the system's tasks to a JSON file.
     * 6. Exit the menu.
     *
     * Exceptions:
     * - If user input is not an integer, the loop iterates without executing any action.
     */
    public static void mainMenu() {
        boolean running = true;
        CreepingSystem creepingSystem = new CreepingSystem(new Kernel());
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== WELCOME TO CREEPING SYSTEM ====");
        System.out.println("==== MAIN MENU ====");

        while (running) {
            System.out.println("1 - Start Creeping System");
            System.out.println("2 - Stop Creeping System");
            System.out.println("3 - Add Task");
            System.out.println("4 - Show Memory Usage Chart");
            System.out.println("5 - Save System to JSON");
            System.out.println("6 - Exit");

            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        creepingSystem.start();
                        break;
                    case 2:
                        creepingSystem.stop();
                        break;
                    case 3:
                        addTask(creepingSystem, scanner);
                        break;
                    case 4:
                        showMemoryUsageChart();
                        break;
                    case 5:
                        SaveToJson.saveSystem(tasks);
                        System.out.println("System saved to JSON.");
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } else {
                System.out.println("Invalid option");
                scanner.next();
            }
        }
    }

    /**
     * Adds a new task to the CreepingSystem and the task list. The method prompts
     * the user for task details such as description, priority, duration, and memory size.
     *
     *  The task is then created and added to both the CreepingSystem and the task queue within
     * the main application.
     *
     * Input validation is performed for priority, duration, and memory size, and any invalid
     * inputs will result in the task not being added.
     *
     * @param creepingSystem The instance of CreepingSystem to which the task is added.
     * @param scanner        The Scanner instance used to read user input for task details.
     */
    private static void addTask(CreepingSystem creepingSystem, Scanner scanner) {
        System.out.println("Enter the task description:");
        String name = scanner.next();
        int priorityOption = -1;

        while (priorityOption < 0 || priorityOption > 2) {
            System.out.println("Enter Task Priority (0-HIGH, 1-MEDIUM, 2-LOW):");
			if (scanner.hasNextInt()) {
            	priorityOption = scanner.nextInt();
			}  else {
				System.out.println("Invalid option");
				scanner.next();
			}
        }

        Priority priority = Priority.values()[priorityOption];
        System.out.println("Enter the task duration (ms):");
        long duration;

        try {
            duration = scanner.nextLong();
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration. Task not added.");
            return;
        }

        System.out.println("Enter task memory size (MB): ");
        int memorySize;
        try {
            memorySize = scanner.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Invalid memory size. Task not added.");
            return;
        }

        Task task = new Task(name, priority);
        task.setDuration(duration);
        task.setMemorySize(memorySize);

        creepingSystem.addTask(task);
        tasks.addToRear(task);
        System.out.println("Task added successfully.");

        memoryUsageData.add(memorySize);

    }

    /**
     * Displays a memory usage chart in a new window.
     *
     * The method executes the GUI-related operations on the Event Dispatch Thread (EDT)
     * to ensure thread safety, using SwingUtilities.invokeLater.
     *
     * The memory usage data used for the chart is expected to be sourced from the
     * memoryUsageData field of the containing class.
     */
    private static void showMemoryUsageChart() {
        SwingUtilities.invokeLater(() -> {
            MemoryUsageChart example = new MemoryUsageChart("Memory Usage Chart", memoryUsageData);
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
