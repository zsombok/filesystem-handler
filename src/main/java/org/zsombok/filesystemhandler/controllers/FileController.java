package org.zsombok.filesystemhandler.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zsombok.filesystemhandler.models.GenerateRequest;
import org.zsombok.filesystemhandler.models.dtos.HistoryDto;
import org.zsombok.filesystemhandler.services.FileService;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @GetMapping("/getUnique")
  public ResponseEntity<Set<String>> getUniqueFiles(
      @RequestParam("folder") String path, @RequestParam("ext") String extension) {
    try {
      return ResponseEntity.ok(fileService.getUniqueFiles(path, extension));
    } catch (JsonProcessingException e) {
      log.error("Error while processing JSON", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/history")
  public ResponseEntity<List<HistoryDto>> getHistory() {
    return ResponseEntity.ok(fileService.getHistory());
  }

  @PostMapping("/gen")
  @Operation(
      summary = "Generate folders and file",
      description =
          "Generates a series of subfolders with the given depth and a file with the given extension in the last folder. The folders and the file have UUID names. You find a form to post requests under the homepage of the application.")
  public ResponseEntity<HttpStatus> generateFile(@RequestBody GenerateRequest request) {
    try {
      fileService.generateFile(request);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      log.error("Error while generating file", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
