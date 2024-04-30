package org.zsombok.filesystemhandler.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Service
public class EnvironmentService {

  @Value("${USER}")
  private String currentUser;
}
