package org.dilmac.simplekafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.dilmac.simplekafka.SimpleKafkaApplication;
import org.dilmac.simplekafka.domain.IssNow;
import org.dilmac.simplekafka.domain.IssNow.IssPosition;
import org.dilmac.simplekafka.service.ConsumerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@MockBean(Producer.class)
@EmbeddedKafka(
    topics = {"iss-position"},
    partitions = 1)
@SpringBootTest(
    webEnvironment = RANDOM_PORT,
    classes = SimpleKafkaApplication.class,
    properties = {
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.auto-offset-reset: earliest"
    })
class ConsumerIntegrationTest {

  @Captor
  ArgumentCaptor<IssNow> argumentCaptor;
  @MockBean
  private ConsumerService consumerService;
  @Autowired
  private KafkaTemplate<String, IssNow> kafkaTemplate;

  private static void accept(SendResult<String, IssNow> result) {
    log.info("Successfully sent message to iss-position::positions: {}", result);
  }

  private static Function<Throwable, Void> reject(IssNow msg) {
    return throwable -> {
      log.warn("Failed to send message to iss-position::positions: {}", msg, throwable);
      return null;
    };
  }

  @Test
  public void shouldConsumeMessage() {
    var msg =
        IssNow.builder()
            .issPosition(IssPosition.builder().latitude(123.0f).longitude(345.0f).build())
            .message("message")
            .timestamp(23424234)
            .build();

    kafkaTemplate
        .send("iss-position", msg)
        .thenAccept(ConsumerIntegrationTest::accept)
        .exceptionally(reject(msg));

    await()
        .atMost(10, SECONDS)
        .untilAsserted(() -> verify(consumerService).handleMessage(argumentCaptor.capture()));

    var actual = argumentCaptor.getValue();

    assertThat(actual).isEqualTo(msg);
  }
}
