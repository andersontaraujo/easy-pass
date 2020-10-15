package br.com.devaware.easypass.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpleProducer {

    @Value("${kafka.topics.firstTopicName}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessage(String messageAsJson) {
        template.send(topicName, messageAsJson);
    }

}
