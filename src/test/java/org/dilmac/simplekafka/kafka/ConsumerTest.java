package org.dilmac.simplekafka.kafka;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.dilmac.simplekafka.domain.IssNow;
import org.dilmac.simplekafka.service.ConsumerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsumerTest {

  @InjectMocks private Consumer tested;

  @Mock private ConsumerService consumerService;

  @Test
  void shouldListen() {
    var msg = mock(IssNow.class);

    doNothing().when(consumerService).handleMessage(msg);

    tested.listen(msg);

    verify(consumerService, times(1)).handleMessage(msg);
  }
}
