package com.activemq.activemq.controller;

import com.activemq.activemq.entities.Student;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@RestController
@RequestMapping("/raw/")
public class RawController {

  /*
   * Note this is not working yet because in the listener there is only students entity handled.
   * */
  @Autowired JmsTemplate jmsTemplate;

  @PostMapping("v1")
  public void saveRawOnDefaultDestination(@RequestParam("message") final String message) {
    jmsTemplate.send(getMessageCreator(message));
  }

  @PostMapping("v2")
  public void saveRawOnSpecifiedDestination(@RequestParam("message") final String message) {
    Destination destination = new ActiveMQQueue("message_queue");
    jmsTemplate.send(destination, getMessageCreator(message));
  }

  @PostMapping("v3")
  public void saveRawOnSpecified(@RequestParam("message") final String message) {
    jmsTemplate.send("message_queue", getMessageCreator(message));
  }

  private MessageCreator getMessageCreator(final String message) {
    return new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        return session.createObjectMessage(message);
      }
    };
  }
}
