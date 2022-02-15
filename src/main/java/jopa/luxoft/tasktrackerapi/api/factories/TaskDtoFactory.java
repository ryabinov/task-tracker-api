package jopa.luxoft.tasktrackerapi.api.factories;

import jopa.luxoft.tasktrackerapi.api.dto.TaskDto;
import jopa.luxoft.tasktrackerapi.api.dto.TaskStateDto;
import jopa.luxoft.tasktrackerapi.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity){
        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createAt(entity.getCreateAt())
                .description(entity.getDescription())
                .build();
    }
}
