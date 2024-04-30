package org.zsombok.filesystemhandler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zsombok.filesystemhandler.models.History;

public interface HistoryRepository extends JpaRepository<History, Long> {}
