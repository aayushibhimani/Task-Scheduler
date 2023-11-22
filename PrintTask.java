// PrintTask.java
import java.util.concurrent.atomic.AtomicInteger;

public class PrintTask implements Task {
    private static final AtomicInteger taskCounter = new AtomicInteger(0);
    private final String message;

    public PrintTask(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Task Message: " + message + " - Thread: " + Thread.currentThread().getName());
        incrementTaskCounter();
    }

    private static void incrementTaskCounter() {
        taskCounter.incrementAndGet();
    }

    public static int getTaskCount() {
        return taskCounter.get();
    }

    public static void resetTaskCounter() {
        taskCounter.set(0);
    }
}
