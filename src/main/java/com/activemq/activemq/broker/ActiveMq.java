package com.activemq.activemq.broker;

import com.activemq.activemq.entities.Student;
import com.activemq.activemq.service.DatabaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ActiveMq {

  DatabaseService databaseService;
  @JmsListener(destination = "message_queue")
  public void saveStudents(Student student) {
    databaseService.save(student);
    log.info(String.valueOf(student));
  }
}
