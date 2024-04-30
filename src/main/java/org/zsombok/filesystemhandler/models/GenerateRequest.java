package org.zsombok.filesystemhandler.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@ToString
public class GenerateRequest {

  private String path;
  private int depth;
  private String extension;
}
