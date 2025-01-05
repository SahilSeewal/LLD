package model;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private String queueName;
    private List<Topic> topicList;

    public Queue(String queueName) {
        this.queueName = queueName;
        this.topicList = new ArrayList<>(); 
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void addTopic(Topic topic) {
        this.topicList.add(topic);
    }
}
