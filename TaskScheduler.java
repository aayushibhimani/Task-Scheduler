// TaskScheduler.java
import java.util.List;
import java.util.concurrent.*;

public class TaskScheduler {
    private final ScheduledExecutorService executorService;
    private final List<ScheduledFuture<?>> scheduledTasks;

    public TaskScheduler(int threadPoolSize) {
        this.executorService = Executors.newScheduledThreadPool(threadPoolSize);
        this.scheduledTasks = new CopyOnWriteArrayList<>();
    }

    public void scheduleAtFixedRate(Task task, long initialDelay, long period, TimeUnit timeUnit) {
        ScheduledFuture<?> scheduledTask = executorService.scheduleAtFixedRate(task::execute, initialDelay, period, timeUnit);
        scheduledTasks.add(scheduledTask);
    }

    public void cancelTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < scheduledTasks.size()) {
            ScheduledFuture<?> task = scheduledTasks.get(taskIndex);
            task.cancel(false);
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public List<ScheduledFuture<?>> getScheduledTasks() {
        return scheduledTasks;
    }
}
