package br.com.facef.rabbitmqparkinglot.controller;

import br.com.facef.rabbitmqparkinglot.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages/send")
public class ParkingLotController {

    @Autowired
    private MessageProducer messageProducer;

    @GetMapping
    public ResponseEntity sendMessage() {
        messageProducer.sendMessage();
        return ResponseEntity.accepted().build();
    }

}
