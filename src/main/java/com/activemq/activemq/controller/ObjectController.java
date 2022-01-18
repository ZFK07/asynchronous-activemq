package com.activemq.activemq.controller;

import com.activemq.activemq.entities.Student;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@RestController
@RequestMapping("/object/")
public class ObjectController {

  @Autowired JmsTemplate jmsTemplate;

  @PostMapping("v1")
  public Mono<ResponseEntity<String>> saveAtDefaultAddress(@RequestBody final Student student) {
    jmsTemplate.convertAndSend(student);
    return Mono.just(new ResponseEntity<>(HttpStatus.OK));
  }

  @PostMapping("v2")
  public Mono<ResponseEntity<String>> saveAtDestinationAddress(@RequestBody final Student student) {
    Destination destination = new ActiveMQQueue("message_queue");
    jmsTemplate.convertAndSend(destination, student);
    return Mono.just(new ResponseEntity<>(HttpStatus.OK));
  }

  @PostMapping("v3")
  public Mono<ResponseEntity<String>> saveAtSpecifiedAddress(@RequestBody final Student student) {
    jmsTemplate.convertAndSend("message_queue", student);
    return Mono.just(new ResponseEntity<>(HttpStatus.OK));
  }


}
