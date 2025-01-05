package lock_semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i=0;i<3;i++) {
            executorService.execute(() -> {
                executeSemaphore(semaphore);
            });
        }
        executorService.shutdown();    
    }

    private static void executeSemaphore(Semaphore semaphore) {
        try {
            semaphore.acquire(2);
            Thread.sleep(10000);
            System.out.println("I am thread: " + Thread.currentThread().getName());
            semaphore.release(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
