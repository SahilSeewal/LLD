//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//public class Main {
//    public static void main(String[] args) {
//        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
//        // to see how IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");
//
//        for (int i = 1; i <= 5; i++) {
//            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
//            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
//            System.out.println("i = " + i);
//        }
//    }
//}

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

 class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException { // Step 1: Asynchronously simulate a task that fetches data
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() ->
        {
            System.out.println("Fetching data...");

            try {
                Thread.sleep(2000);
                // Simulate some delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 10;
        }); // Step 2: After fetching data, process it asynchronously
        CompletableFuture<Integer> future2 = future1.thenApplyAsync(result ->
        {
            System.out.println("Processing data...");
            return result * 2; // Simulate processing by doubling the value
        });
        // Step 3: After processing, handle the result or exception
        future2.thenAccept(result -> {
            System.out.println("Final result: " + result);
            // This should print 20 (10 * 2)
        });
        // Step 4: Handle any exception that occurred in the task
        future2.exceptionally(ex -> {
            System.out.println("An error occurred: " + ex.getMessage());
            return null;
        });
		
        // Wait for the final result to complete (Blocking call)
        future2.join();
    }
}
