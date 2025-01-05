package model;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private String topicName;
    private List<TopicSubscriber> topicSubscriberList;
    private List<Message> messageList;

    public Topic(String topicName) {
        this.topicName = topicName;
        this.messageList = new ArrayList<>();
        this.topicSubscriberList = new ArrayList<>();
    }

    public String getTopicName() {
        return this.topicName;
    }
    
    public  void addSubscriber(Subscriber subscriber) {
        topicSubscriberList.add(new TopicSubscriber(subscriber));
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public List<TopicSubscriber> getTopicSubscriberList() {
        return this.topicSubscriberList;        
    }

    public List<Message> getMessageList() {
        return this.messageList;        
    }
}
