package br.com.facef.rabbitmqparkinglot.producer;

import br.com.facef.rabbitmqparkinglot.configuration.ParkingLotConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Integer messageNumber = 0;

    public void sendMessage() {
        rabbitTemplate
            .convertAndSend(
                ParkingLotConfiguration.EXCHANGE_MESSAGES,
                ParkingLotConfiguration.QUEUE_MESSAGES,
                "Some message id: " + messageNumber++
            );
    }

}
