package com.activemq.activemq.controller;

import com.activemq.activemq.entities.Student;
import com.activemq.activemq.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.util.Optional;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static reactor.core.publisher.Mono.delay;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ObjectControllerTest {

  @LocalServerPort private int port;

  @InjectMocks ObjectController objectController;

  @Autowired MockMvc mockMvc;

  @Autowired StudentRepository studentRepository;

  @Test
  void saveAtDefaultAddress() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String json =
        objectMapper.writeValueAsString("{\n" + "\"name\":\"Zahid\",\n" + "\"fees\":14\n" + "}");

    ResultActions perform = mockMvc.perform(
            post("/v3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .characterEncoding("utf-8"));

    Optional<Student> any =
        studentRepository.findAll().stream().filter(student -> student.getFees() == 14).findAny();
    Assertions.assertTrue(any.isPresent());
  }

  @Test
  void saveAtDestinationAddress() {}

  @Test
  void saveAtSpecifiedAddress() {}
}
