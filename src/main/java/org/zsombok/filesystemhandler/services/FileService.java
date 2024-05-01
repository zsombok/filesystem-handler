package org.zsombok.filesystemhandler.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zsombok.filesystemhandler.models.GenerateRequest;
import org.zsombok.filesystemhandler.models.dtos.HistoryDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

  private final HistoryService historyService;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public Set<String> getUniqueFiles(String path, String extension) throws JsonProcessingException {
    log.info("getUniqueFiles called with path: {} and extension: {}", path, extension);
    File folder = new File(path);
    if (!folder.exists() || !folder.isDirectory()) {
      throw new IllegalArgumentException("Folder does not exist or is not a directory");
    }
    Set<String> files = getFiles(folder, extension, new HashSet<>());
    log.info("Unique {} files in {}: {}", extension, path, files);
    historyService.createAndSaveHistory(path, extension, objectMapper.writeValueAsString(files));
    return files;
  }

  private Set<String> getFiles(File file, String extension, HashSet<String> uniqueFileNames) {
    File[] files = file.listFiles();
    if (files == null) {
      return Set.of();
    }
    for (File f : files) {
      if (f.isDirectory()) {
        getFiles(f, extension, uniqueFileNames);
      } else {
        if (f.getName().endsWith(extension)) {
          uniqueFileNames.add(f.getName());
        }
      }
    }
    return uniqueFileNames;
  }

  public List<HistoryDto> getHistory() {
    log.info("getHistory called");
    return historyService.findAll().stream().map(HistoryDto::new).toList();
  }

  public void generateFile(GenerateRequest request) {
    log.info("generateFile called with: {}", request);
    generateFile(request.getPath(), request.getDepth(), request.getExtension());
  }

  private void generateFile(String folder, int depth, String extension) {
    StringBuilder sb = new StringBuilder(folder);
    for (int i = 0; i <= depth; i++) {
      sb.append(File.separator).append(UUID.randomUUID());
    }
    sb.append('.').append(extension);
    String filePath = sb.toString();
    log.info("Creating file: {}", filePath);
    Path pathToFile = Paths.get(filePath);
    try {
      Files.createDirectories(pathToFile.getParent());
      Files.createFile(pathToFile);
    } catch (Exception e) {
      log.error("Error while creating file", e);
    }
  }
}
