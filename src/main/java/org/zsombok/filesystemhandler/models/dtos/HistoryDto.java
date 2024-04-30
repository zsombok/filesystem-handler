package org.zsombok.filesystemhandler.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zsombok.filesystemhandler.models.Action;
import org.zsombok.filesystemhandler.models.History;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

  private String initiator;
  private Long timestamp;
  private Action action;
  private String result;

  public HistoryDto(History history) {
    this.initiator = history.getInitiator();
    this.timestamp = history.getTimestamp();
    this.action = history.getAction();
    this.result = history.getResult();
  }
}
