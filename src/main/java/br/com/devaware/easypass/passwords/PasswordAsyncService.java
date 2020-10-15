package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.messaging.SimpleProducer;
import br.com.devaware.easypass.passwords.dtos.messaging.PasswordMessageDTO;
import br.com.devaware.easypass.passwords.dtos.request.PasswordPartialDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PasswordAsyncService {

    private PasswordService passwordService;

    private SimpleProducer simpleProducer;

    private ObjectMapper objectMapper;

    @Autowired
    public PasswordAsyncService(PasswordService passwordService, SimpleProducer simpleProducer) {
        this.passwordService = passwordService;
        this.simpleProducer = simpleProducer;
        objectMapper = new ObjectMapper();
    }

    public void createPasswordAsync(PasswordPartialDTO request) {

        new Thread(() -> {

            try {

                log.info("PasswordAsyncService.createPasswordAsync - start. Input: [{}], ThreadID: [{}]", request, Thread.currentThread().getId());

                PasswordMessageDTO passwordMessage = passwordService.createPasswordAsync(request);

                String message = objectMapper.writeValueAsString(passwordMessage);

                simpleProducer.sendMessage(message);

                log.info("PasswordAsyncService.createPasswordAsync - end. Input: [{}], Output: [{}], ThreadID: [{}]", request, passwordMessage, Thread.currentThread().getId());

            } catch (KafkaException | IOException e) {
                log.error("PasswordAsyncService.createPasswordAsync - error: {}", e.getMessage(), e);
            }

        }).start();

    }
}
