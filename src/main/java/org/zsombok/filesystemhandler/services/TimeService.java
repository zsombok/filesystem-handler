package org.zsombok.filesystemhandler.services;

import org.springframework.stereotype.Service;

@Service
public class TimeService {

  public long getCurrentMillis() {
    return System.currentTimeMillis();
  }
}
