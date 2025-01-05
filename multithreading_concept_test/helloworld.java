import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.io. * ;
import java.lang. * ;

class helloworld {
    public static void main(String[]args)throws InterruptedException {
        // int a[] = new a[]{10};
        // int a[] = new a[]{10};
        CompletableFuture < Integer > task1 = CompletableFuture.supplyAsync(()->{
            // System.out.println("First " + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName());
            return 10;
        });
        CompletableFuture < Integer > task2 = task1.thenComposeAsync(result->CompletableFuture.supplyAsync(()->{
                    // System.out.println("Second " + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName());
                    return result * 2;
                }));
        CompletableFuture.allOf(task1, task2).join(); // Outputs: 20
        // try{
        // //	System.out.println(task2.get());
        // } catch(Exception ex) {}

    }
}
