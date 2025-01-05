package lock_semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i=0;i<3;i++) {
            executorService.execute(() -> {
                executeLock(lock);
            });
        }
        executorService.shutdown();    
    }

    private static void executeLock(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(10000);
            System.out.println("I am thread: " + Thread.currentThread().getName());
            lock.unlock();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
