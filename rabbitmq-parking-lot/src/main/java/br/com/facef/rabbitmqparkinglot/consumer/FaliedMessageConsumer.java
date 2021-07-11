package br.com.facef.rabbitmqparkinglot.consumer;

import br.com.facef.rabbitmqparkinglot.configuration.ParkingLotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.facef.rabbitmqparkinglot.configuration.ParkingLotConfiguration.EXCHANGE_MESSAGES;
import static br.com.facef.rabbitmqparkinglot.configuration.ParkingLotConfiguration.QUEUE_MESSAGES;
import static br.com.facef.rabbitmqparkinglot.configuration.ParkingLotConfiguration.EXCHANGE_PARKING_LOT;

@Slf4j
@Component
public class FaliedMessageConsumer {

    private static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
    private static final Integer MAX_RETRIES_COUNT = 3;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = ParkingLotConfiguration.QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRequeue(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties()
                .getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null) retriesCnt = 1;
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("Sending message to the parking lot queue");
            rabbitTemplate.send(EXCHANGE_PARKING_LOT,
                    failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
            return;
        }
        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties()
                .getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send(
                EXCHANGE_MESSAGES,
                QUEUE_MESSAGES,
                failedMessage);
    }

}
