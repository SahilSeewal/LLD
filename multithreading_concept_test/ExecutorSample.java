import java.util.concurrent.*;
import java.io.* ;
import java.util.* ;

// public class ExecutorSample {
    // public static void main(String[] args) {
        // // Create a FixedThreadPool with 3 threads
        // ExecutorService executor = Executors.newFixedThreadPool(3);

        // // Submit tasks to the executor
        // for (int i = 0; i < 5; i++) {
            // final int taskId = i;
            // executor.submit(() -> {
                // System.out.println("Task " + taskId + " is running in thread " + Thread.currentThread().getName());
                // try {
                    // Thread.sleep(2000); // Simulate task execution time
                // } catch (InterruptedException e) {
                    // Thread.currentThread().interrupt();
                // }
                // System.out.println("Task " + taskId + " has completed.");
            // });
        // }

        // // Shutdown the executor
        // executor.shutdown();
    // }
// }

public class ExecutorSample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Create a list of tasks
        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(() -> {
            Thread.sleep(2000);
            return "Task 1 completed";
        });
        tasks.add(() -> {
            Thread.sleep(1000);
            return "Task 2 completed";
        });
        tasks.add(() -> {
            Thread.sleep(3000);
            return "Task 3 completed";
        });

        // Execute the tasks and get the result of the first completed task
        String result = executor.invokeAny(tasks);
        System.out.println("First completed task result: " + result);

        // Shutdown the executor
        executor.shutdown();
    }
}

