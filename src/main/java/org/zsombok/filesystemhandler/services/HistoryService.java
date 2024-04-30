package org.zsombok.filesystemhandler.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zsombok.filesystemhandler.models.Action;
import org.zsombok.filesystemhandler.models.History;
import org.zsombok.filesystemhandler.repositories.HistoryRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class HistoryService {

  private final HistoryRepository historyRepository;
  private final EnvironmentService environmentService;
  private final TimeService timeService;

  public void createAndSaveHistory(String path, String extension, String result) {
    log.info("createAndSaveHistory called with path: {} and extension: {}", path, extension);
    History history =
        History.builder()
            .initiator(environmentService.getCurrentUser())
            .timestamp(timeService.getCurrentMillis())
            .action(new Action(path, extension))
            .result(result)
            .build();
    historyRepository.save(history);
  }

  public List<History> findAll() {
    return historyRepository.findAll();
  }
}
