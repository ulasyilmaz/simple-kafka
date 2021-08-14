package org.dilmac.simplekafka.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonNaming(SnakeCaseStrategy.class)
public class IssNow implements Serializable {

  IssPosition issPosition;
  String message;
  long timestamp;

  @Value
  @Builder
  @Jacksonized
  @JsonNaming(SnakeCaseStrategy.class)
  public static class IssPosition {

    float longitude;
    float latitude;
  }
}
