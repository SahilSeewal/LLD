package pattern.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import pattern.constants.Type;
import pattern.model.Semaphore;
import pattern.service.PatternService;

public class PatternTest {
    public static void main(String[] args){
        Type sharedType = Type.ODD;
        Semaphore semaphore = new Semaphore(sharedType);
        PatternService patternService = new PatternService(semaphore);

        
        CompletableFuture<Void> oddFuture = CompletableFuture.runAsync(() -> {
            // patternService.printPattern(Type.ODD, Type.EVEN, new AtomicInteger(1), new AtomicInteger(50));
            patternService.printPattern(Type.ODD, Type.EVEN, 1, 50);
        });
        
        CompletableFuture<Void> evenFutue = CompletableFuture.runAsync(() -> {
            // patternService.printPattern(Type.EVEN, Type.ODD, new AtomicInteger(2), new AtomicInteger(50));
            patternService.printPattern(Type.EVEN, Type.ODD, 2, 50);
        });
        CompletableFuture.allOf(oddFuture, evenFutue).join();
    }
}
