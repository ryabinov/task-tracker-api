package jopa.luxoft.tasktrackerapi.store.repositories;

import jopa.luxoft.tasktrackerapi.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
