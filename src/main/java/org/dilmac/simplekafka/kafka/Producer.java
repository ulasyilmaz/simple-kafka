package org.dilmac.simplekafka.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dilmac.simplekafka.domain.IssNow;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

  private final KafkaTemplate<String, IssNow> kafkaTemplate;

  @Scheduled(fixedDelay = 5000)
  public void scheduleFixedDelayTask() {
    WebClient.create()
        .get()
        .uri("http://api.open-notify.org/iss-now.json")
        .retrieve()
        .bodyToMono(IssNow.class)
        .doOnNext(data -> log.info("Successfully retrieved ISS data from open-notify: {}", data))
        .doOnNext(this::sendMessage)
        .block();
  }


  private void sendMessage(IssNow msg) {
    kafkaTemplate
        .send("iss-position", msg)
        .thenAccept(Producer::accept)
        .exceptionally(reject(msg));
  }

  private static void accept(SendResult<String, IssNow> result) {
    log.info("Successfully sent message to iss-position::positions: {}", result);
  }

  private static Function<Throwable, Void> reject(IssNow msg) {
    return throwable -> {
      log.warn("Failed to send message to iss-position::positions: {}", msg, throwable);
      return null;
    };
  }
}
