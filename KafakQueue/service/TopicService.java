package service;

import model.Message;
import model.Subscriber;
import model.Topic;

public class TopicService {
    private SubscriberService subscriberService;
    
    public TopicService() {
        this.subscriberService = new SubscriberService();
    }

    public void addSubscriber(Topic topic, Subscriber subscriber) {
        topic.addSubscriber(subscriber);
    }

    public void addMessage(Topic topic, Message message) {
        topic.addMessage(message);
        new Thread(()-> {
            subscriberService.createSubscriberWorkers(topic.getTopicName(), topic.getTopicSubscriberList(), topic.getMessageList());
        }).start();
    }
}
