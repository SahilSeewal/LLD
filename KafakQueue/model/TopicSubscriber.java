package model;

import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber {
    private Subscriber subscriber;
    private AtomicInteger offset;

    public TopicSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = new AtomicInteger(0);
    }

    public Subscriber getsubscriber() {
        return this.subscriber;
    }

    public AtomicInteger getOffset() {
        return this.offset;
    }
}
