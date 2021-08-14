package org.dilmac.simplekafka.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dilmac.simplekafka.domain.IssNow;
import org.dilmac.simplekafka.service.ConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

  private final ConsumerService consumerService;

  @KafkaListener(topics = "iss-position", groupId = "positions")
  public void listen(IssNow msg) {
    consumerService.handleMessage(msg);
  }
}
