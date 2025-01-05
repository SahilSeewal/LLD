package service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import model.Message;
import model.TopicSubscriber;

// import repository.SubscriberWorkerRepo;

public class SubscriberService {
    private ExecutorService executorService;
    private ExecutorService[] threads;

    public SubscriberService()  {
        // this.executorService = Executors.newFixedThreadPool(100);
        this.threads = new ExecutorService[100];
        for(int i=0; i<100; i++) {
            this.threads[i] = Executors.newSingleThreadExecutor();
        }

    }
    
    public void createSubscriberWorkers(String name, List<TopicSubscriber> topicSubscriberList, List<Message> messageList) {
        
        System.out.println( Thread.currentThread().getName()+" Consuming messages from topic: " + name);
        for(TopicSubscriber topicSubscriber: topicSubscriberList) {
            int idx = new Random().nextInt(100);
            System.out.println("thread idx: " + (int)Math.random()*100);
            CompletableFuture.runAsync(()->{consume(topicSubscriber, messageList, name);}, threads[idx]);
            // if(!SubscriberWorkerRepo.getSubscriberWorkerMap().contains(subscriber)) {
            //     SubscriberWorkerRepo.getSubscriberWorkerMap().put(subscriber, new Worker(subscriber));
            //     new Thread(() -> {
            //         SubscriberWorkerRepo.getSubscriberWorkerMap().get(subscriber);
            //     }).start(); 
            
            // WorkerService workerService = new WorkerService();

            // executorService.execute(
            //     ()->{consume(topicSubscriber, messageList, name);});
             }

            
            // else {
            //     SubscriberWorkerRepo.getSubscriberWorkerMap().get(subscriber).awakeIfWaiting();
            // }
        }
        
        // protected void finalize() throws Throwable {
        //     System.out.println("myshoes object is destroyed");
        //     executorService.shutdown();
        // }
        

        private void consume(TopicSubscriber topicSubscriber, List<Message> messageList, String topicName) {     
            synchronized(topicSubscriber) {   
            while(topicSubscriber.getOffset().get() < messageList.size()) {
                int currOffset = topicSubscriber.getOffset().get();     
            try {
            // System.out.println("Subscriber :" + topicSubscriber.getsubscriber().getName() + " is consuming message at idx: " + topicSubscriber.getOffset().get());
            
                Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " Subscriber :" + topicSubscriber.getsubscriber().getName() + " consumed from idx: " + currOffset + " from topic " + topicName);
            topicSubscriber.getOffset().compareAndSet(currOffset, currOffset + 1);
            
            } catch (Exception e) {
                System.out.println(e);
            }
                        
        }
        }
        }
}    

