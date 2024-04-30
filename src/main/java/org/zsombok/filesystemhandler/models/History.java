package org.zsombok.filesystemhandler.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zsombok.filesystemhandler.utils.converters.ActionConverter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String initiator;
  private Long timestamp;

  @Convert(converter = ActionConverter.class)
  private Action action;

  private String result;
}
