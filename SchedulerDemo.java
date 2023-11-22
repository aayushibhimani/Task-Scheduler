import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SchedulerDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Task Scheduler Demo!");

        System.out.print("Enter thread pool size: ");
        int threadPoolSize = scanner.nextInt();

        TaskScheduler scheduler = new TaskScheduler(threadPoolSize);
        long startTime = System.currentTimeMillis();

        while (true) {
            System.out.println("\n1. Schedule Tasks");
            System.out.println("2. Cancel Task");
            System.out.println("3. Display Scheduled Tasks");
            System.out.println("4. Shutdown Scheduler");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter number of tasks to schedule: ");
                    int numberOfTasks = scanner.nextInt();
                    for (int i = 0; i < numberOfTasks; i++) {
                        System.out.print("Enter task message for task " + (i + 1) + ": ");
                        String taskMessage = scanner.next();
                        scheduler.scheduleAtFixedRate(new PrintTask(taskMessage), 0, 2, TimeUnit.SECONDS);
                    }
                    System.out.println(numberOfTasks + " tasks scheduled successfully!");
                    break;

                case 2:
                    System.out.print("Enter task index to cancel: ");
                    int taskIndex = scanner.nextInt();
                    scheduler.cancelTask(taskIndex);
                    System.out.println("Task canceled successfully!");
                    break;

                case 3:
                    System.out.println("Scheduled Tasks: " + scheduler.getScheduledTasks().size());
                    break;

                case 4:
                    scheduler.shutdown();
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;

                    int totalTasksExecuted = PrintTask.getTaskCount();
                    double throughput = totalTasksExecuted / (duration / 1000.0);

                    System.out.println("Scheduler shutdown successfully!");
                    System.out.println("Test Duration: " + duration + " ms");
                    System.out.println("Throughput: " + throughput + " tasks/sec");
                    scanner.close();
                    System.exit(0);

                case 5:
                    System.out.println("Exiting Task Scheduler Demo!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


/*
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger; // Add import statement


public class SchedulerDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Task Scheduler Demo!");

        System.out.print("Enter thread pool size: ");
        int threadPoolSize = scanner.nextInt();

        TaskScheduler scheduler = new TaskScheduler(threadPoolSize);
        long startTime = System.currentTimeMillis();

        while (true) {
            System.out.println("\n1. Schedule Task");
            System.out.println("2. Cancel Task");
            System.out.println("3. Display Scheduled Tasks");
            System.out.println("4. Shutdown Scheduler");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter task message: ");
                    String taskMessage = scanner.next();
                    scheduler.scheduleAtFixedRate(new PrintTask(taskMessage), 0, 2, TimeUnit.SECONDS);
                    System.out.println("Task scheduled successfully!");
                    break;

                case 2:
                    System.out.print("Enter task index to cancel: ");
                    int taskIndex = scanner.nextInt();
                    scheduler.cancelTask(taskIndex);
                    System.out.println("Task canceled successfully!");
                    break;

                case 3:
                    List<ScheduledFuture<?>> scheduledTasks = scheduler.getScheduledTasks();
                    System.out.println("Scheduled Tasks: " + scheduledTasks.size());
                    break;

                case 4:
                    scheduler.shutdown();
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;

                    // Calculate and print throughput
                    int totalTasksExecuted = PrintTask.getTaskCount();
                    double throughput = totalTasksExecuted / (duration / 1000.0); // tasks per second

                    System.out.println("Scheduler shutdown successfully!");
                    System.out.println("Test Duration: " + duration + " ms");
                    System.out.println("Throughput: " + throughput + " tasks/sec");
                    scanner.close();
                    System.exit(0);

                case 5:
                    System.out.println("Exiting Task Scheduler Demo!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
*/