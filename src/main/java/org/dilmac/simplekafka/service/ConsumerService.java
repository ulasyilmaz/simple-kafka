package org.dilmac.simplekafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dilmac.simplekafka.domain.IssNow;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerService {

  public void handleMessage(IssNow msg) {
    log.info("Successfully received message from iss-position::positions: {}", msg);
  }
}
