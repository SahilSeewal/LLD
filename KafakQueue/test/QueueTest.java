package test;

import model.Message;
import model.Queue;
import model.Subscriber;
import model.Topic;
import service.QueueService;
import service.SubscriberService;
import service.TopicService;

public class QueueTest {
    public static void main(String[] args) {
        Queue queue = new Queue("Kafka");
        QueueService queueService = new QueueService();
        TopicService topicService = new TopicService();
        // SubscriberService subscriberService = new SubscriberService();

        Topic topic1 = new Topic("t1");
        Topic topic2 = new Topic("t2");

        Subscriber subscriber1 = new Subscriber("s1");
        Subscriber subscriber2 = new Subscriber("s2");
        Subscriber subscriber3 = new Subscriber("s3");

        topicService.addSubscriber(topic1, subscriber1);
        topicService.addSubscriber(topic1, subscriber2);
        topicService.addSubscriber(topic2, subscriber3);

        Message message1 = new Message("m1");
        Message message2 = new Message("m2");
        Message message3 = new Message("m3");
        Message message4 = new Message("m4");

        queueService.addTopic(queue, topic1);
        queueService.addTopic(queue, topic2);

        topicService.addMessage(topic1, message1);
        topicService.addMessage(topic1, message2);
        topicService.addMessage(topic2, message3);
        topicService.addMessage(topic2, message4);

        
        System.err.println("Main thread exited!");
        System.gc();
        //t1 -> m1, m2 -> s1, s2
        // t2 -> m3, m4 -> s3

        // subscriberService.changeOffset(subscriber1, 2);        
    }
}
