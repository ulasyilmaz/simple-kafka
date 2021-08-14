package org.dilmac.simplekafka.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.dilmac.simplekafka.domain.IssNow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class ConsumerServiceTest {

  @InjectMocks private ConsumerService tested;

  @Test
  void shoulHandleMessage(CapturedOutput output) {
    var msg = mock(IssNow.class);

    tested.handleMessage(msg);

    assertThat(output.getOut())
        .contains("Successfully received message from iss-position::positions");
  }
}
