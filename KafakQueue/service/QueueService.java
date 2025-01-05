package service;

import model.Queue;
import model.Topic;

public class QueueService {
    
    public void addTopic(Queue queue, Topic topic) {
        queue.addTopic(topic);
    }
}
