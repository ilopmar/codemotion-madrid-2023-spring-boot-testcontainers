package com.codemotion.demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Testcontainers
public abstract class AbstractContainerTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:15.2"));

  @Container
  @ServiceConnection
  static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.5"));

  //  @DynamicPropertySource
  //  static void init(DynamicPropertyRegistry registry) {
  //    Startables.deepStart(postgreSQLContainer, kafka).join();
  //
  //    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
  //    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
  //    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  //
  //    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
  //  }

  protected RequestSpecification requestSpecification;

  @LocalServerPort
  int port;

  @BeforeEach
  void setup() {
    requestSpecification = new RequestSpecBuilder()
        .setPort(port)
        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

}
