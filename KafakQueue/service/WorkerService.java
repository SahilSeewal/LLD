package service;

import java.util.List;

import model.Message;
import model.TopicSubscriber;

public class WorkerService {
    // public void consume(TopicSubscriber topicSubscriber, List<Message> messageList, String topicName) {     
    //     synchronized(topicSubscriber) {   
    //     while(topicSubscriber.getOffset().get() < messageList.size()) {
    //         int currOffset = topicSubscriber.getOffset().get();     
    //     try {
    //     // System.out.println("Subscriber :" + topicSubscriber.getsubscriber().getName() + " is consuming message at idx: " + topicSubscriber.getOffset().get());
        
    //         Thread.sleep(2000);
    //     System.out.println(Thread.currentThread().getName() + " Subscriber :" + topicSubscriber.getsubscriber().getName() + " consumed from idx: " + currOffset + " from topic " + topicName);
    //     topicSubscriber.getOffset().compareAndSet(currOffset, currOffset + 1);
        
    //     } catch (Exception e) {
    //         System.out.println(e);
    //     }
                    
    // }
    // }
    // }
}
